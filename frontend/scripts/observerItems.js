const elements = document.querySelectorAll('.faq-view-faqTab');
console.log("yay I am here");
console.log("The length of the elements " + elements.length);
// Create an IntersectionObserver instance
const observer = new IntersectionObserver(entries => {
    entries.forEach(entry => {
        if (entry.isIntersecting) {
            // If the element is entering the viewport, add the appropriate class
            if (entry.boundingClientRect.top < 0) {
                entry.target.classList.add('animate-down');
                console.log("I am adding classnames for animation down")
            } else {
                entry.target.classList.add('animate-up');
                console.log("I am adding classnames for animation up")
            }
            observer.unobserve(entry.target);
        }
    });
});

// Observe each element
elements.forEach(element => {
    observer.observe(element);
});
