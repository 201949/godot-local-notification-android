# godot-local-notification-android
Modified version of the Godot Local Notification plugin for Android from DrMoriarty


## Usage

Add wrapper `scripts/LN.gd` into autoloading list in your project. So you can use it everywhere in your code. Configure the script parameters as you wish.

## API

### showLocalNotification(message: String, title: String, interval: float, tag: int)

Show notification with `title` and `message` after delay of `interval` seconds with `tag`. You can override notification by it's tag before it was fired.

### showRepeatingNotification(message: String, title: String, interval: float, tag: int, repeat_duration: int)
Show notification with `title` and `message` after delay of `interval` seconds with `tag`. You can override notification by it's tag before it was fired.
`repeating_interval` the notification will be fired in a loop until you cancelled it.


### show_daily(message: String, title: String, hour: int, minute: int, tag: int = 1)
(IOS Only)
Show notification daily at specific hour and minute (in 24 hour format).
You can overide the notification with new time, or cancel it with tag and register a new one.

*Need help*: Currently just support ios, need help on Android

### cancelLocalNotification(tag: int)

Cancel previously created notification.

### cancelAllNotifications()

Cancel all pending notifications (implemented for iOS only).

### init()

Request permission for notifications (iOS only).

### is_inited() -> bool

Check if notification permission was requested from user (iOS only).

### is_enabled() -> bool

Check if notification permission was granted by user (iOS only).

### register_remote_notification()

Request system token for push notifications. (iOS only)

### get_device_token() -> String

Returns system token for push notification.

### get_notification_data() -> Dictionary

Returns custom data from activated notification (Android only).

### get_deeplink_action() -> String

Returns action from deeplink, if exists. (Android only).

### get_deeplink_uri() -> String

Returns deeplink URI, if exists (Android only).

## Customising notifications for Android

The default notification color is defined in `android/build/res/values/notification-color.xml`. You can change it at your desire. The color string format is `#RRGGBB`.

In order to change default notification icon you should make this new files:
```
android/build/res/mipmap/notification_icon.png            Size 192x192
android/build/res/mipmap-hdpi-v4/notification_icon.png    Size 72x72
android/build/res/mipmap-mdpi-v4/notification_icon.png    Size 48x48
android/build/res/mipmap-xhdpi-v4/notification_icon.png   Size 96x96
android/build/res/mipmap-xxhdpi-v4/notification_icon.png  Size 144x144
android/build/res/mipmap-xxxhdpi-v4/notification_icon.png Size 192x192
```
Notification icons should be b/w with alpha channel. They will be tinted with color which we discuss above.


## Compiling

Prerequisites:

- Android SDK (platform version 33)
- the Godot Android library (`godot-lib.***.release.aar`) for your version of Godot from the [downloads page](https://godotengine.org/download).

Steps to build:

1. Clone this Git repository
2. Put `godot-lib.***.release.aar` in `./libs/`
3. Run `./gradlew build` in the cloned repository

If the build succeeds, you can find the resulting `.aar` files in `./godot-local-notification-android/build/outputs/aar/`.
