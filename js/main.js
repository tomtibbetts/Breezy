(function () {
  const header = document.querySelector(".site-header");
  const menuToggle = document.querySelector(".menu-toggle");
  const nav = document.querySelector(".site-nav");
  const searchOpen = document.querySelectorAll("[data-open-search]");
  const infoOpen = document.querySelectorAll("[data-open-info]");
  const overlays = document.querySelectorAll(".overlay");
  const closeButtons = document.querySelectorAll("[data-close-overlay]");
  const forms = document.querySelectorAll("[data-aui-form]");
  const filters = document.querySelectorAll("[data-filter]");
  const newsCards = document.querySelectorAll("[data-category]");

  const current = document.body.dataset.page;
  document.querySelectorAll(".nav-list a").forEach((link) => {
    if (link.dataset.page === current) {
      link.setAttribute("aria-current", "page");
    }
  });

  window.addEventListener("scroll", () => {
    header?.classList.toggle("is-scrolled", window.scrollY > 8);
  });

  menuToggle?.addEventListener("click", () => {
    const open = nav?.classList.toggle("is-open");
    document.body.classList.toggle("nav-open", open);
    menuToggle.setAttribute("aria-expanded", String(Boolean(open)));
  });

  function openOverlay(id) {
    document.getElementById(id)?.classList.add("is-open");
    document.body.classList.add("nav-open");
  }

  function closeOverlays() {
    overlays.forEach((overlay) => overlay.classList.remove("is-open"));
    if (!nav?.classList.contains("is-open")) {
      document.body.classList.remove("nav-open");
    }
  }

  searchOpen.forEach((btn) => btn.addEventListener("click", () => openOverlay("searchOverlay")));
  infoOpen.forEach((btn) => btn.addEventListener("click", () => openOverlay("infoOverlay")));
  closeButtons.forEach((btn) => btn.addEventListener("click", closeOverlays));
  overlays.forEach((overlay) => {
    overlay.addEventListener("click", (event) => {
      if (event.target === overlay) closeOverlays();
    });
  });

  document.addEventListener("keydown", (event) => {
    if (event.key === "Escape") {
      closeOverlays();
      nav?.classList.remove("is-open");
      document.body.classList.remove("nav-open");
    }
  });

  forms.forEach((form) => {
    form.addEventListener("submit", (event) => {
      event.preventDefault();
      const success = form.parentElement.querySelector(".form-success");
      success?.classList.add("is-visible");
      form.reset();
    });
  });

  filters.forEach((button) => {
    button.addEventListener("click", () => {
      filters.forEach((item) => item.classList.remove("is-active"));
      button.classList.add("is-active");
      const value = button.dataset.filter;
      newsCards.forEach((card) => {
        card.hidden = value !== "all" && card.dataset.category !== value;
      });
    });
  });
})();
