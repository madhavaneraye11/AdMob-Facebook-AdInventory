# Ad Integration App (AdMob + Facebook)

This Android application demonstrates how to integrate and display ads from both **AdMob** and **Facebook** simultaneously with the same display ratio. The ad unit IDs for both platforms are fetched dynamically from **Firebase Remote Config**, allowing for real-time changes without requiring app updates. Additionally, the app tracks ad impressions and sends events to **Firebase Analytics** for further analysis.

## Features

- **AdMob and Facebook Ads**: Displays both **Banner** and **Interstitial** ads from AdMob and Facebook at the same time with an equal probability.
- **Remote Config**: Fetches ad unit IDs for AdMob and Facebook from **Firebase Remote Config** dynamically.
- **Ad Impressions Tracking**: Sends ad impression events to **Firebase Analytics** to track ad views.
- **Two Types of Ads**:
  - **Banner Ads**: Displayed at the top or bottom of the screen.
  - **Interstitial Ads**: Full-screen ads shown at natural breakpoints within the app.

## Tech Stack

- **Kotlin**: Language used for Android development.
- **Firebase Remote Config**: Used to fetch dynamic ad unit IDs.
- **Firebase Analytics**: Used to track ad impressions.
- **AdMob**: Google's advertising platform for displaying banner and interstitial ads.
- **Facebook Audience Network**: Facebook's platform for showing banner and interstitial ads.
- **Jetpack Libraries**: For modern Android development and UI design.
  
## App Flow

1. **Fetch Ad Unit IDs**: 
   - The app fetches the **AdMob** and **Facebook** ad unit IDs from **Firebase Remote Config**.
   
2. **Show Ads**:
   - The app displays **Banner Ads** from both AdMob and Facebook with a 50% display ratio.
   - It shows **Interstitial Ads** from either AdMob or Facebook at random, based on the same 50% chance.
   
3. **Track Impressions**: 
   - Every time an ad is shown, the app sends an event to **Firebase Analytics** to track ad impressions for analysis.

## Screenshots

Here are some screenshots of the app:

| Screen                      | Description                      |
| --------------------------- | -------------------------------- |
| ![Banner Ads](assets/screenshots/banner.png) | Banner ads from AdMob and Facebook at the same time |
| ![Interstitial Ad](assets/screenshots/interstitial.png) | Interstitial ad displayed randomly from AdMob or Facebook |

## Installation

### Prerequisites

- **Android Studio** installed on your computer.
- **Firebase Project** set up for Remote Config and Analytics.

### Steps to Set Up

1. **Clone the repository**:
   ```bash
   git clone https://github.com/yourusername/ad-integration-app.git
   ```

2. **Open the project in Android Studio**.

3. **Set up Firebase**:
   - Create a new Firebase project in the [Firebase Console](https://console.firebase.google.com/).
   - Add Firebase to your Android app by downloading the `google-services.json` file and placing it in the `app/` directory.
   - Enable **Firebase Remote Config** and **Firebase Analytics** in your Firebase project.
   - Set up the **AdMob** and **Facebook Audience Network** in your Firebase project.
   
4. **Configure Remote Config**:
   - Set up keys in **Firebase Remote Config** to fetch the **AdMob** and **Facebook** ad unit IDs (e.g., `admob_banner_id`, `facebook_banner_id`, `admob_interstitial_id`, `facebook_interstitial_id`).

5. **Sync the Project with Gradle**:
   - Ensure that Firebase and AdMob dependencies are included in the `build.gradle` file.

6. **Run the App**:
   - Build and run the app on your emulator or device.

## Code Structure

- **MainActivity.kt**: Handles displaying ads (both banner and interstitial) and managing the display logic.
- **FirebaseUtils.kt**: Handles fetching ad unit IDs from **Firebase Remote Config**.
- **AdTrackingUtils.kt**: Sends ad impression events to **Firebase Analytics**.

## Future Enhancements

- **Dynamic Ad Frequency**: Allow for changing the frequency of interstitial ads through **Remote Config**.
- **Ad Mediation**: Implement ad mediation to allow a mix of multiple ad networks for better monetization.

## Contributing

Feel free to fork the repository and submit pull requests for improvements or bug fixes. If you want to contribute to this project, please follow these steps:

1. Fork the repository.
2. Create a new branch for your feature (`git checkout -b feature-name`).
3. Make your changes and commit them (`git commit -am 'Add new feature'`).
4. Push to your branch (`git push origin feature-name`).
5. Open a pull request.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Contact

- **Author**: Madhav Aneraye
- **Email**: madhavaneraye11@gmail.com 
- **LinkedIn**: https://www.linkedin.com/in/madhav-aneraye-a152aa13a/

---

Thanks for checking out this app! Feel free to explore the code and modify it for your needs. This project demonstrates how to integrate AdMob and Facebook ads while leveraging Firebase Remote Config for dynamic configuration and Firebase Analytics for tracking ad performance.
```

### Key Points of the README:
- **Features**: A short summary of the key functionalities of the app (showing ads from both platforms, fetching ad unit IDs remotely, tracking impressions).
- **Tech Stack**: Technologies and tools used (AdMob, Facebook Audience Network, Firebase, etc.).
- **App Flow**: Brief explanation of how the app works (fetching ad unit IDs, showing ads, and tracking impressions).
- **Installation Instructions**: Steps to set up the project locally.
- **Code Structure**: Outline of where the key logic resides in the code.
- **Future Enhancements**: Ideas for improving the app.
- **Contributing and License**: Instructions on how to contribute and license details.
