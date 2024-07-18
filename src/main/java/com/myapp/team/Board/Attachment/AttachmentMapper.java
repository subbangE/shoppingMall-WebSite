package com.myapp.team.Board.Attachment;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AttachmentMapper {

    void insertAttachment(Attachment attachment);

    List<Attachment> getAttachmentsByNo(int questionNo);

    Attachment selectAttachmentByNo(int attachmentNo);

    void deleteAttachmentByNo(int attachmentNo);
}