import { Component, HostListener } from '@angular/core';
import { Router } from '@angular/router';
import { InternService } from './intern.service';
import { FormGroup } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'relntern';
  private timeoutId: any;
  readonly TIMEOUT_DURATION = 15 * 60 * 1000; // 15 minutes in milliseconds

  constructor(private internService: InternService, private router: Router) {
    this.startTimer();
  }

  // Function to reset the timer whenever user activity is detected
  @HostListener('window:mousemove')
  @HostListener('window:keydown')
  @HostListener('window:click')
  resetTimer() {
    clearTimeout(this.timeoutId);
    this.startTimer();
  }

  // Function to start the session timeout timer
  startTimer() {
    this.timeoutId = setTimeout(() => {
      this.autoLogout();
    }, this.TIMEOUT_DURATION);
  }

  // Function to handle automatic logout when session expires
  autoLogout() {
    console.log('Your session has expired due to inactivity. You will be logged out automatically.');
    localStorage.clear(); // Clear any stored user data
    this.router.navigate(['/login']); // Redirect to the login page
    window.location.reload(); // Reload the page to reflect the logout status
  }

  // Original register method
  register(registerForm: FormGroup) {
    this.internService.registerIntern(registerForm.value).subscribe(
      (resp) => {
        console.log(resp);
      },
      (err) => console.log(err)
    );
  }
}
