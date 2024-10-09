import { Component, OnInit, resolveForwardRef } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../service/auth.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { User } from './user';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  user?: User;
  model: any;
  getData: boolean = true;
  username: any;
  password: any;
  role: any;
  userId: any;
  isAdmin: boolean = false;
  isMentor: boolean = false;
  isIntern: boolean = false;
  toastr: any;
  captchaResolved = false;
  captchaToken: string | null = null;
  

  otpCode: string = '';
  otpModalVisible: boolean = false;

  errorMessage: string | null = null;  // New property for storing error message
  loading: boolean = false;

  constructor(private authservice: AuthService, private router: Router) {}

  onCaptchaResolved(captchaResponse: string): void {
    this.captchaToken = captchaResponse;
    this.captchaResolved = true;
  }

  // loginUser() {
  //   console.log(this.username, this.password);
  //   // debugger
  //   // var username = username;
  //   // var password = password;
  //   if (!this.captchaResolved) {
  //     console.log('Please complete the CAPTCHA');
  //     return;
  //   }
  //   let userJson: any = {
  //     username: this.username,
  //     password: this.password,
  //     caotchaToken: this.captchaToken,
  //   };

  //   this.authservice.postUserData(userJson).subscribe((data) => {
  //     //console.log(data.role);

  //     let userId = data.id;
  //     // console.log(userId);

  //     if (data.role === 'admin') {
  //       this.isAdmin = true;
  //       console.log('admin:' + this.isAdmin);
  //       localStorage.setItem('role', 'admin');
  //       this.openOtpModal();
  //     } else if (data.role === 'mentor') {
  //       this.isMentor = true;
  //       console.log('mentor:' + this.isMentor);
  //       console.log(data.id);
  //       localStorage.setItem('role', 'mentor');
  //       localStorage.setItem('userId', userId.toString());
  //       localStorage.setItem('mentorEmail', data.username);
  //       this.openOtpModal();
  //       //  this.reloadPage();
  //     } else if (data.role === 'intern') {
  //       this.isIntern = true;
  //       // console.log(data.id);

  //       console.log('Intern:' + this.isIntern);
  //       localStorage.setItem('role', 'intern');
  //       localStorage.setItem('userId', userId.toString());
  //       this.openOtpModal();
  //     } else {
  //       this.toastr.error('password Incorrect');
  //       console.log('password Incorrect');
  //     }
  //   });
  // }
  // Function to open the OTP modal
  loginUser() {
    this.errorMessage = null; // Clear the error message when user tries again
    this.loading = true;
    console.log(this.username, this.password);
  
    if (!this.captchaResolved) {
      console.log('Please complete the CAPTCHA');
      this.loading = false;
      return;
    }
  
    let userJson: any = {
      username: this.username,
      password: this.password,
      captchaToken: this.captchaToken,
    };

    this.authservice.postUserData(userJson).subscribe(
      (data) => {
        // Check if the user object is valid
        if (data && data.role) {
          let userId = data.id;
  
          if (
            data.role === 'admin' ||
            data.role === 'mentor' ||
            data.role === 'intern'
          ) {
            localStorage.setItem('role', data.role);
            localStorage.setItem('userId', userId.toString());
            localStorage.setItem('username', data.username);
  
            // Set the appropriate role flag
            this.isAdmin = data.role === 'admin';
            this.isMentor = data.role === 'mentor';
            this.isIntern = data.role === 'intern';
  
            // Request OTP after successful login
            this.authservice.requestOtp({ username: data.username }).subscribe(
              () => {
                this.loading = false;
                this.openOtpModal(); // Open OTP modal after OTP is sent
              },
              (error) => {
                this.loading = false;
                console.log('Error sending OTP:', error);
              }
            );
          } else {
            this.loading = false;
            this.errorMessage = 'Invalid email or password, please enter again'; // Set error message
            console.log('Incorrect password');
          }
        } else {
          this.loading = false;
          this.errorMessage = 'Invalid email or password, please enter again'; // Set error message if user is not found
        }
      },
      (error) => {
        this.loading = false;
        this.errorMessage = 'Invalid email or password, please enter again'; // Set error message on API failure
        console.log('Login failed:', error);
      }
    );
  }
  


  openOtpModal() {
    this.otpModalVisible = true;
  }

  closeOtpModal() {
    this.otpModalVisible = false;
  }

  verifyOtp() {
    if (this.otpCode.length !== 5) {
      console.log('Please enter a valid 5-digit OTP');
      return;
    }

    let otpJson: any = {
      username: this.username,
      otp: this.otpCode,
    };

    this.authservice.verifyOtp(otpJson).subscribe((response) => {
      if (response) {
        console.log(response);
        this.closeOtpModal(); // Close OTP modal after successful verification
        this.redirectUser(); // Redirect to appropriate dashboard
      } else {
        this.toastr.error('Invalid OTP');
      }
    });
  }

  redirectUser() {
    if (this.isAdmin) {
      this.goToPage('dashboard');
    } else if (this.isMentor) {
      this.goToPage('mentordashboard');
    } else if (this.isIntern) {
      this.goToPage('interndashboard');
    }
  }

  reloadPage() {
    window.location.reload();
  }

  ngOnInit() {}

  goToPage(pageName: string): void {
    this.router.navigate([`${pageName}`]);
  }
}