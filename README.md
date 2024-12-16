## **Creating a Firebase Project**

1. **Logging into Firebase Console**  
   Go to the [Firebase Console](https://console.firebase.google.com/).

2. **Creating a New Project**  
   On the Firebase Console homepage, click the **"Add Project"** button in the top right corner. Then, follow the steps that appear to create a new project. Complete the process by entering the project name and other required information.

3. **Setting Up the Firebase Project**  
   Once the project is created, it will be active in the Firebase Console.

4. **Enabling Firebase Authentication**  
   After setting up the project, click on the **Authentication** tab in the left menu. Then, click the **"Get Started"** button to enable the Authentication feature.


   ---

## **Linking Android Studio Project to Firebase Project**

1. **Clicking on Firebase Tab**  
   In Android Studio, click on the **Firebase** tab in the top menu.

2. **Accessing the Authentication Tab**  
   Click on the **Authentication** tab in the left menu. This allows us to configure the necessary settings to integrate Firebase Authentication with the project.

3. **Choosing Custom Authentication System**  
   Under the Authentication section, click on **Authenticate using a custom authentication system**.

4. **Connecting to Firebase**  
   In the window that appears, click the **Connect to Firebase** button. This step will link your Android Studio project with your Firebase project.

5. **Adding the SDK to the Project**  
   After connecting to Firebase, click on **Add the Firebase Authentication SDK to your project**. This will add the required Firebase Authentication dependencies to your project.

6. **Accepting Changes**  
   After adding the dependencies, click on **Accept Changes** to confirm the necessary configurations and file changes.

7. **Logging into Firebase Console**  
   Go to the Firebase Console and continue working on your project.

8. **Configuring Authentication Settings**  
   In the Firebase Console, click on the **Authentication** tab from the left menu and then go to the **Sign-in method** section.

9. **Enabling Email/Password Authentication**  
   Under the **Sign-in method** section, enable the **Email/Password** option. This will allow users to log into the application using their email and password.

---

## **Firebase Authentication Configuration**


### AuthViewModel Class

We create an `AuthViewModel` class to manage user operations with Firebase Authentication. This class handles user registration, login, and password reset processes.

### **Firebase AuthState Class**

We define an `AuthState` class to track different states during user operations. This class manages states such as success, error, or loading during the process.


## User Registration and Login Screen

You can use an example screen structure to handle user registration and login. We call the Firebase Authentication API by taking the user's email and password.

## Testing the Connection to Firebase Authentication

You can test the functionality of Firebase Authentication by performing registration and login operations. You can monitor the status of your users and the results of the operations through the Firebase Console.



