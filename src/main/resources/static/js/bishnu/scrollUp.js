window.addEventListener('DOMContentLoaded', function() {
    function updateScroller() { 
    var yScrollPos = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop;
    var scrollHight = document.querySelector('.scroll-button');
    if (yScrollPos > 150) {
        scrollHight.style.display = 'block';
    } else {
        scrollHight.style.display = 'none';
    }
    };
    updateScroller();
    window.addEventListener('scroll', updateScroller);
    });
    
    function scrollToTop() {
          // Scroll to the top of the page
          var delay = 500;
          setTimeout(function() {
              window.scrollTo({
                    top: 0,
                    behavior: 'smooth'
                  }); 
          }, delay);
        }