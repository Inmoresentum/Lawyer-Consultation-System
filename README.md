<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Lawyer Consultation</title>
  </head>
  <body>
    <div class="container">
      <h1 class="shape">
        <span>L</span>
        <span>a</span>
        <span>w</span>
        <span>y</span>
        <span>e</span>
        <span>r</span>
        <span>&nbsp;</span>
        <span>C</span>
        <span>o</span>
        <span>n</span>
        <span>s</span>
        <span>u</span>
        <span>l</span>
        <span>t</span>
        <span>a</span>
        <span>t</span>
        <span>i</span>
        <span>o</span>
        <span>n</span>
      </h1>
      <p>CSE471 PROJECT</p>
      <h2>Tech Stack</h2>
      <ul>
        <li>
          <i class="fab fa-java"></i> Java
        </li>
        <li>
          <i class="fab fa-spring"></i> Spring Boot
        </li>
        <li>
          <i class="fab fa-vuejs"></i> Vaadin Flow
        </li>
        <li>
          <i class="fas fa-file-code"></i> LitElements
        </li>
        <li>
          <i class="fas fa-database"></i> MariaDB
        </li>
        <li>
          <i class="fas fa-cloud-upload-alt"></i> MinIO
        </li>
        <li>
          <i class="fab fa-css3-alt"></i> Vanilla CSS
        </li>
      </ul>
      <h2>Team Members</h2>
      <ul>
        <li>Athar Noor Mohammad Rafee [20101396]</li>
        <li>Faria Islam Laiba [20301176]</li>
        <li>Shornali Islam Snikdha [20301108]</li>
      </ul>
      <h2>Status</h2>
      <p>Currently the project is at a very early stage</p>
    </div>
<script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/js/all.min.js" integrity="sha512-2bMhOkE/ACz21dJT8zBOMgMecNxx0d37NND803ExktKiKdSzdwn+L7i9fdccw/3V06gM/DBWKbYmQvKMdAA9Nw==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
  </body>
</html>

<style>
@keyframes gradient {
  0% {
    background-position: 0% 50%;
  }
  50% {
    background-position: 100% 50%;
  }
  100% {
    background-position: 0% 50%;
  }
}

body {
  background: linear-gradient(-45deg, #ee7752, #e73c7e, #23a6d5, #23d5ab);
  background-size: 400% 400%;
  animation: gradient 15s ease infinite;
  font-family: Arial, sans-serif;
  margin: 0;
}

.container {
  max-width: 800px;
  margin: 0 auto;
  padding: 50px 20px;
  text-align: center;
  color: #fff;
}

h1 {
  font-size: 3.5rem;
  color: #fff;
  text-transform: uppercase;
  letter-spacing: 1.5px;
  margin-top: 2rem;
  position: relative;
  display: inline-block;
}

/* Add the hacker text effect on hover */
.container h1:hover span {
  /*animation: hackerEffect 2s ease-in-out forwards;*/
  animation: colorChange 2s ease-in-out forwards;
}

h1 span {
  font-weight: bold;
}

p {
  font-size: 2.5rem;
  margin-bottom: 40px;
}

.container h1 span:before {
  content: attr(data-letter);
  position: absolute;
  top: 0;
  left: 0;
  opacity: 0;
  color: #f00;
  animation: colorChange 2s ease-in-out forwards;
}

ul {
  list-style: none;
  padding: 0;
  margin: 0;
  display: flex;
  justify-content: center;
}

li {
  font-size: 1.25rem;
  margin-right: 20px;
}

i {
  margin-right: 5px;
  font-size: 1.5rem;
}

h2 {
  font-size: 2rem;
  margin-bottom: 20px;
}

ul:nth-child(2) {
  margin-bottom: 40px;
}

/* Add the glitch animation keyframes */
@keyframes hackerEffect {
  0% {
    color: #fff;
  }
  10% {
    color: #0f0;
    transform: scale(1.2);
  }
  20% {
    color: #fff;
    transform: scale(1);
  }
  30% {
    color: #0f0;
    transform: scale(1.2);
  }
  40% {
    color: #fff;
    transform: scale(1);
  }
  50% {
    color: #0f0;
    transform: scale(1.2);
  }
  60% {
    color: #fff;
    transform: scale(1);
  }
  70% {
    color: #0f0;
    transform: scale(1.2);
  }
  80% {
    color: #fff;
    transform: scale(1);
  }
  90% {
    color: #0f0;
    transform: scale(1.2);
  }
  100% {
    color: #fff;
    transform: scale(1);
  }
}

@keyframes colorChange {
  0% {
    opacity: 0;
  }
  10% {
    opacity: 1;
    color: #ff0;
  }
  20% {
    color: #f00;
  }
  30% {
    color: #0f0;
  }
  40% {
    color: #00f;
  }
  50% {
    color: #ff0;
  }
  60% {
    color: #f00;
  }
  70% {
    color: #0f0;
  }
  80% {
    color: #00f;
  }
  90% {
    color: #ff0;
  }
  100% {
    color: #f00;
  }
}
</style>

<script>
    const text = document.querySelector('.container p');
    const depth = 10;

    document.addEventListener('mousemove', e => {
        const x = e.clientX;
        const y = e.clientY;

        const centerX = window.innerWidth / 2;
        const centerY = window.innerHeight / 2;

        const deltaX = centerX - x;
        const deltaY = centerY - y;

        const rotateX = (deltaY / centerY) * depth;
        const rotateY = (deltaX / centerX) * depth;

        text.style.transform = `translateZ(${depth}px) rotateX(${rotateX}deg) rotateY(${rotateY}deg)`;
    });
</script>