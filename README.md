# social-media-application-project


<div id="top"></div>

<!-- PROJECT LOGO -->
<br />
<div align="center">
  <h3 align="center"> social media project </h3>
<h4 align="center">Homework</h4>

  <p align="center">
    ·
    <a href="https://github.com/seymailbay/social-media-application-project/issues">Report Bug</a>
    ·
    <a href="https://github.com/seymailbay/social-media-application-project">Request Feature</a>
  </p>


</div>





<!-- ABOUT THE PROJECT -->
## About The Project

A small social media project was done.

*The information of social media users will be stored in a database using PostgreSQL.

*Users will be able to register and log in to the system by entering their username and password.

*Once a username is taken by a user, it cannot be used by anyone else.

*Posts, comments, and likes will be visible to everyone on the homepage.

*The buttons for posting, commenting, and liking will only be available when a user is logged in.

*The date of posting and commenting will be stored in the database.

*The length of posts and comments will be limited to 250 words.

*Users will be able to select their own avatar on their profile page, and it will be saved in the database.

*Users will be able to view their notifications in the activity section of their profile page.

<p align="right">(<a href="#top">back to top</a>)</p>


<!-- TEST SCENARIOS-->
## TEST SCENARIOS

User registration:
Verify that a new user can successfully register on the platform by providing a unique username and password.
Verify that a user cannot register with an already taken username.
Verify that the user is redirected to the login page after successful registration.

User login:
Verify that a registered user can log in to the platform by entering their username and password.
Verify that an incorrect username or password results in an error message and the user cannot log in.
Verify that the user is redirected to their profile page after successful login.

Posting:
Verify that a logged-in user can create a new post with a maximum of 250 words.
Verify that the post is displayed on the homepage for all users to see.
Verify that the date and time of the post are saved in the database.
Verify that the post cannot be created if the user is not logged in.

Commenting:
Verify that a logged-in user can comment on a post with a maximum of 250 words.
Verify that the comment is displayed on the post for all users to see.
Verify that the date and time of the comment are saved in the database.
Verify that the comment cannot be created if the user is not logged in.

Liking:
Verify that a logged-in user can like a post.
Verify that the number of likes for a post is updated in real-time.
Verify that a user can only like a post once.
Verify that the like button is not available if the user is not logged in.

Avatar:
Verify that a user can upload and save their own avatar image to their profile page.
Verify that the avatar is displayed correctly on the profile page.
Verify that the avatar is saved in the database.
Verify that the avatar upload feature is only available for logged-in users.


<!-- PREVIEWS -->

## Previews

![Login Page](https://github.com/seymailbay/social-media-application-project/blob/main/preview/loginpage.PNG)

![HomePage](https://github.com/seymailbay/social-media-application-project/blob/main/preview/postpage.PNG)

![Profile Page](https://github.com/seymailbay/social-media-application-project/blob/main/preview/changeavatar.PNG)

![Posts](https://github.com/seymailbay/social-media-application-project/blob/main/preview/psotpage2.PNG)

<p align="right">(<a href="#top">back to top</a>)</p>
