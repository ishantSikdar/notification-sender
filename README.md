﻿# notification-sender

This is a demo Java Spring Boot application to generate FCM notifications.
The App uses REST API to send a notification with a random meme embed in it.

Firebase configurations are setup in `FirebaseConfig.class`, and its implementation is done on `FirebaseServiceImpl.java`,
A Java Bean is already created that will be used to use Firebase Services throughout the Application.

```java
    @Bean
    FirebaseMessaging firebaseMessaging() throws IOException {
        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource("firebase-service-account.json").getInputStream());
        FirebaseOptions firebaseOptions = FirebaseOptions
                .builder()
                .setCredentials(googleCredentials)
                .build();

        FirebaseApp app = FirebaseApp.initializeApp(firebaseOptions);
        return FirebaseMessaging.getInstance(app);
    }
```

### Quickstart
First, you need atleast `JDK 17` or above and `Maven` installed on your machine.

Then you must provide your Firebase Account Credentials, if you dont have, then generate from `firebase.google.com`,
After getting your own firebase account and credentials of a new project, create a file at `/src/main/resources/` called `firebase-service-account.json`.
Then fill the following data with your Firebase account keys.

```json
{
  "type": "your_type",
  "project_id": "your_project_id",
  "private_key_id": "your_private_key_id",
  "private_key": "your_private_key",
  "client_email": "your_client_mail",
  "client_id": "your_client_id",
  "auth_uri": "your_auth_uri",
  "token_uri": "your_token_uri",
  "auth_provider_x509_cert_url": "your_auth_provider_x509_cert_url",
  "client_x509_cert_url": "your_client_x509_cert_url",
  "universe_domain": "your_universal_domain"
}
```

### API
The App has a public REST API endpoint ``{DOMAIN}/api/v1/sendNotifications``,
Method: `POST`, Request Body: RAW JSON Body of structure:

```json
{
  "fcmToken" : "your_unique_app_device_token_generated_by_firebase"
}
```

So all the client-app is need to do is sent its `fcmToken` as a Request Body 
field into this API to get a random meme notification.

### Dependencies

App requires `Java 17` or above installed on the machine and uses 
2 Dependencies imported from Maven Central called `firebase-admin` and `spring-webflux`.

`firebase-admin` is used to generate and sent push notifications to the located device using FCM TOKEN,
and `spring-webflux` is used to call external APIs, in our case, its used to get a random meme from `https://meme-api.com/gimme` using `GET` method.

