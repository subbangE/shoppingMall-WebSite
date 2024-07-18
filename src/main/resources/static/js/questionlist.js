document.addEventListener("DOMContentLoaded", function() {
    const userFrames = document.querySelectorAll(".user-frame");

    userFrames.forEach(frame => {
        const isAnswered = frame.getAttribute("data-answered") === "true";
        const statusIcon = frame.querySelector(".status-icon");

        if (isAnswered) {
            statusIcon.classList.add("completed");
        }
    });
});