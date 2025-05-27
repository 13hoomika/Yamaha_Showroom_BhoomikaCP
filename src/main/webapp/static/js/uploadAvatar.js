   function uploadAvatar() {
     const form = document.getElementById('avatarUploadForm');
     const fileInput = document.getElementById('avatarInput');
     if (fileInput.files.length > 0) {
         form.submit();
     }
   }