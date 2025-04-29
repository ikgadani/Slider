Slider is a comprehensive Java-based web application that showcases the integration of multiple modern technologies to manage and display sliders across different platforms. The project features user authentication, database management, internationalization, and cross-platform support, including a web app, Android app, and a Java Swing desktop client.

Features

🌐 Web Application

  Authentication Integration:

  Admin Users: Full access to all application pages.
  
  JSFGroup Users: Restricted access to JSF-based pages only.
  
  RestGroup Users: Access limited to REST API endpoints (usable through tools like Postman).
  
  React Integration:
  
  A React-based page dynamically lists all sliders stored in the database.
  
  Implements visually engaging slider animations using HTML5 Canvas.
  
  CRUD Functionality:
  
  Create: Add new sliders through web forms.
  
  Read: View a list of all existing sliders.
  
  Update: Edit existing slider details.
  
  Delete: Remove sliders from the database.
  
  Internationalization (i18n):
  
  Supports dynamic language switching within the application.

✅ Testing
  
  Selenium Tests:

  Automated testing of the authentication workflow to ensure secure and correct user access.
  
  JUnit Tests:
  
  Comprehensive unit and integration tests covering:
  
  POST: Add new sliders.
  
  GET: Retrieve slider information.
  
  PUT: Update slider details.
  
  DELETE: Remove sliders.
  
📱 Android Application

  Built using Kotlin and Java.
  
  Dynamically fetches and lists all sliders from the backend service.
  
🖥️ Java Swing Desktop Application

  Developed using Java Swing.
  
  Provides a simple GUI to update slider details directly from a desktop environment.
  
Technologies Used

  Area: Technology
  Backend:	Java, REST API
  Frontend: React, JavaServer Faces (JSF), HTML5 Canvas
  Database:	MySQL
  Testing:	Selenium, JUnit
  Mobile Development:	Kotlin, Java
  Desktop Application:	Java Swing
