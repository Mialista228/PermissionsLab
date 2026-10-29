# PermissionsLab

## Tested API Levels
- **API 32 (Android 12L):** Used for testing normal permissions and pre-Tiramisu notification behavior (where `POST_NOTIFICATIONS` is granted automatically at installation).
- **API 34 (Android 14):** Used for testing modern runtime permissions including Camera handling, Special Overlay permissions, and one-time location behaviors.

## Task 5 Observation: One-Time Location
When a user selects "Only this time" for location permissions, the system grants the permission temporarily while the application session remains active in the foreground. However, once the app is sent to the background and the process or session context terminates/expires, returning via `ON_RESUME` confirms that the runtime permission state automatically reverts to revoked (`none`), requiring a fresh prompt upon subsequent foreground entries.

## Task 6 Observation: Version-Dependent Notifications
On API Level 32 emulators, notifications are dispatched directly without any runtime prompt because notification permissions were bundled into install-time permissions. On API Level 34 emulators, invoking `POST_NOTIFICATIONS` triggers a system runtime dialogue prompt; notifications are dropped or suppressed until the user explicitly taps "Allow".

## P.s. Answers to theoretical questions in Permissions Practice.pdf
