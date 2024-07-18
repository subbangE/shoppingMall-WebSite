package com.myapp.team.Board.Question;

import com.myapp.team.Board.Attachment.Attachment;
import com.myapp.team.Board.Attachment.AttachmentService;
import com.myapp.team.user.config.CustomUserDetails;
import com.myapp.team.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

//질문 보는 거 - 상품 안에서
//질문 작성 - 새로운 url
//질문 수정, 삭제 - 마이페이지

@Controller
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private AttachmentService attachmentService;

    private final Path AttachmentStorageLocation = Paths.get(System.getProperty("user.dir"), "src", "main", "java", "com", "myapp", "team", "Board", "Attachment", "uploads").toAbsolutePath().normalize();

    // 회원일련번호별로 질문한 것들 가져오게 하는 컨트롤러 => URL 수정해야함 마이페이지용
    @GetMapping("/mypage/{userNo}")
    public String getQuestion(@PathVariable("userNo") int userNo, Model model) {
        List<Question> userquestionList = questionMapper.selectQuestionById(userNo);
        model.addAttribute("userquestionList", userquestionList);
//        System.out.println(userquestionList);
        return "MypageBoard";
    }

    // 질문 번호, 질문 제목 및 회원일련번호만 가져오게 하는 컨트롤러
    @GetMapping
    public String getQuestion(Model model,
                              @RequestParam(defaultValue = "1") int page) {
        List<Question> questionList = questionService.getQuestions(page);
        model.addAttribute("questionList", questionList);
        model.addAttribute("currentPage", page);
        System.out.println(questionList);
        return "Board";
    }

    // 질문과 답변 하나씩 가져 오게 하는 컨트롤러 (QuestionService 사용)
    @GetMapping("/{questionNo}")
    public String showQuestionDetailForm(@PathVariable int questionNo, Model model) {
        Question question = questionService.getQuestionById(questionNo);


        // 현재 로그인된 사용자 정보를 가져와 질문 userNo와 비교하여 수정 및 삭제 가능하게 함
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userDetails.getUser();
        int currentUserNo = currentUser.getUserNo();

        boolean isAdmin = userDetails.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ADMIN"));

        boolean canEditOrDelete = question.getUserNo() == currentUserNo;
        question.setCanEditOrDelete(canEditOrDelete);

        // 뷰에서 사용하기 위해 가져옴
        model.addAttribute("question", question);
        model.addAttribute("canEditOrDelete", canEditOrDelete);
        model.addAttribute("isAdmin", isAdmin);

        System.out.println(question);

        return "QuestionDetail";
    }

    // 첨부파일 다운로드 컨트롤러
    @GetMapping("/download/{attachmentNo}")
    public ResponseEntity<Resource> downloadAttachment(@PathVariable int attachmentNo) throws MalformedURLException, UnsupportedEncodingException {
        Attachment attachment = attachmentService.getAttachmentNo(attachmentNo);
        Path attachmentPath = Paths.get(attachment.getAttachmentPath()).toAbsolutePath().normalize();
        Resource resource = new UrlResource(attachmentPath.toUri());

        if (resource.exists()) {
            String encodedAttachmentName = URLEncoder.encode(attachment.getOriginalFilename(), StandardCharsets.UTF_8.toString());
            String contentDisposition = "attachment; filename*=UTF-8''" + encodedAttachmentName;

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                    .body(resource);
        } else {
            throw new RuntimeException("File not found " + attachment.getStoredFilename());
        }

    }

    // 질문 생성 페이지 보여주는 컨트롤러
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        String username = userDetails.getUsername();
        int userNo = userDetails.getUserNo();
        model.addAttribute("username", username);
        model.addAttribute("userNo", userNo);
        model.addAttribute("question", new Question());
        return "CreateQuestion";
    }

    //  질문 등록하는 컨트롤러 => URL("/question/create/{prodId}")
    @PostMapping("/create")
    public String createQuestion(@RequestParam("questionTitle") String questionTitle,
                                 @RequestParam("questionContent") String questionContent,
                                 @RequestParam("userNo") int userNo,
                                 @RequestParam("file")MultipartFile file,
                                 Model model) throws IOException {
        Question question = new Question(questionTitle, questionContent, userNo);
        questionMapper.insertQuestion(question);


        // 첨부 파일이 있다면 DB에 등록(데이터 가져오기)
        if (!file.isEmpty()) {

            String originalFileName = file.getOriginalFilename();
            // 파일명이 중복으로 되지 않게 랜덤값 생성해서 붙여서 저장
            String storedFileName = UUID.randomUUID().toString() + "_" + originalFileName;

            if (!Files.exists(AttachmentStorageLocation)) {
                Files.createDirectory(AttachmentStorageLocation);
            }

            Path AttachmentLocation = AttachmentStorageLocation.resolve(storedFileName); // 변경된 이름으로 저장
            Files.copy(file.getInputStream(), AttachmentLocation);


            Attachment attachment = new Attachment();
            attachment.setQuestionNo(question.getQuestionNo()); // 질문 번호
            attachment.setOriginalFilename(file.getOriginalFilename()); // 원본 파일명
            attachment.setAttachmentPath(AttachmentLocation.toString());    // 파일 위치
            attachment.setStoredFilename(storedFileName);   // 저장된 파일명
            System.out.println(attachment);
            System.out.println(file);
            attachmentService.addAttachment(attachment);
        }
//        return "redirect:/question";
        return "questionSuccess";
    }



    // 수정할 수 있도록 질문 하나씩 가져 오게 하는 컨트롤러 (보여주기용)
    @GetMapping("/update/{questionNo}")
    public String showQuestionUpdateForm(@PathVariable int questionNo, Model model) {
        Question question = questionService.getQuestionById(questionNo);
        model.addAttribute("question", question);
        return "UpdateQuestion";
    }

    // 질문 수정하는 컨트롤러 => URL("/수정하기 아직 모름") 마이페이지에서 수정하기
    @PostMapping("/update/{questionNo}")
    public String editQuestion(@PathVariable("questionNo") int questionNo,
                               @RequestParam("questionTitle") String questionTitle,
                               @RequestParam("questionContent") String questionContent,
                               @RequestParam(value = "deleteAttachments", required = false) List<Integer> deleteAttachments,
                               @RequestParam(value = "newAttachments", required = false) List<MultipartFile> newAttachments) throws IOException {

        questionMapper.updateQuestion(questionNo, questionTitle, questionContent);

        if (deleteAttachments != null) {
            for (int attachmentNo : deleteAttachments) {
                attachmentService.removeAttachment(attachmentNo);
            }
        }

        // 새로운 첨부파일 추가
        if (newAttachments != null) {
            for (MultipartFile file : newAttachments) {
                if (!file.isEmpty()) {
                    String originalFileName = file.getOriginalFilename();
                    String storedFileName = UUID.randomUUID().toString() + "_" + originalFileName;

                    if (!Files.exists(AttachmentStorageLocation)) {
                        Files.createDirectory(AttachmentStorageLocation);
                    }

                    Path attachmentLocation = AttachmentStorageLocation.resolve(storedFileName);
                    Files.copy(file.getInputStream(), attachmentLocation);

                    Attachment attachment = new Attachment();
                    attachment.setQuestionNo(questionNo);
                    attachment.setOriginalFilename(originalFileName);
                    attachment.setStoredFilename(storedFileName);
                    attachment.setAttachmentPath(attachmentLocation.toString());
                    attachmentService.addAttachment(attachment);
                }
            }
        }
        return "redirect:/question/{questionNo}";
    }

    // 질문 삭제하는 컨트롤러 => URL("/삭제하기 아직 모름") 마이페이지에서 삭제하기
    @PostMapping("/delete")
    public String deleteQuestion(@RequestParam int questionNo) {
//        System.out.println("삭제 질문 번호: " + questionNo);
        questionMapper.deleteQuestion(questionNo);
        return "redirect:/";
    }


}
