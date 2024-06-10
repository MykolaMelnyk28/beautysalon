

function useImageModal() {
   /* global bootstrap: false */
   const myModal = new bootstrap.Modal(document.getElementById('myModal'));

   function openModal(button) {
      const imageUrl = button.getAttribute('data-src');
      console.log(imageUrl);
      const modalImage = document.getElementById('modalImage');
      modalImage.src = imageUrl;
      myModal.show();
   }

   function closeModal() {
      myModal.hide();
   }

   const items = document.querySelectorAll(".item-image");
   const closeBtn = document.getElementById("close-btn");

   console.log(items);
   items?.forEach(x => x.addEventListener("click", function (e) {
      openModal(x);
   }));
   closeBtn.addEventListener("click", function () {
      closeModal();
   });
}

function useDropdown() {
   const items = document.getElementsByClassName("item-dropdown");

   let openDropMenu = false;
   let dropdownMenuOpen = null;


   for(let s of items){
      s.addEventListener("contextmenu", function (e) {
         e.preventDefault();
         if (dropdownMenuOpen && openDropMenu) {
            dropdownMenuOpen.classList.remove("d-grid");
            dropdownMenuOpen.classList.add("d-none");
            openDropMenu = false;
            dropdownMenuOpen = null;
         }
         const dropdownMenu = s.querySelectorAll(".dropdown-menu")[0];
         console.log(dropdownMenu);
         dropdownMenu.classList.remove("d-none");
         dropdownMenu.classList.add("d-grid");

         dropdownMenuOpen = dropdownMenu;
         openDropMenu = true;
      });
   }
   document.addEventListener('click', function(event) {
      if (dropdownMenuOpen && openDropMenu) {
         if (event.target !== dropdownMenuOpen && !dropdownMenuOpen.contains(event.target)) {
            dropdownMenuOpen.style.display = 'none';
            dropdownMenuOpen.classList.remove("d-grid");
            dropdownMenuOpen.classList.add("d-none");
         }
      }
   });
}

document.addEventListener("DOMContentLoaded", function () {
   useDropdown();
});
