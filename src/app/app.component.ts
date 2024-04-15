import { Component, HostListener  } from '@angular/core';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'CyberSecNotes';
  isHeaderVisible = true;
  lastScrollTop = 0;
  @HostListener('window:scroll')
  onWindowScroll() {
    const currentScroll = window.pageYOffset || document.documentElement.scrollTop;
    if (currentScroll === 0) {
      this.isHeaderVisible = true;
    } else {
      this.isHeaderVisible = false;
    }
    this.checkScroll();
  }

  checkScroll() {
    console.log("ENTRAA");
    let scrollPosition = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop || 0;
    console.log(scrollPosition);
    const scrollButton = document.querySelector('.scroll-top-button');
    const scrollButton2 = document.querySelector('.scroll-top-button') as HTMLElement;
    if (scrollButton) {
      if (scrollPosition > 300) { // Cambia este valor según tu preferencia
        scrollButton.classList.add('show');
        scrollButton2.style.opacity = '1';
        console.log("ENTRAA IF");
      } else {
        scrollButton.classList.remove('show');
        scrollButton2.style.opacity = '0';
        console.log("ENTRAA ELSE");
      }
    }
  }
  scrollToTop() {
    window.scrollTo({
      top: 0,
      behavior: 'smooth' // Desplazamiento suave hacia arriba
    });
  }
}
