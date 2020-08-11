#README.
1. The program requires Android Studio (preferably the latest version). It also runs best
 on a Pixel 3a XL, with an API edition of 27. However, other devices are also supported.
2. Currently, we do not allow changes in usernames, but we do offer password changing 
functionality. 
3. We had to put two instances of the String keys for the use cases in BundleActivity and in 
ConfigGateway. The other option would have been to make the String keys public and static. 
4. The ObjectsRequireNonNull method is required for Android in order to remove the warnings. There
are also several warnings that Android Studio picked up in Meeting/MeetingManager
with regards to NullPointerExceptions and unboxing, but they could not be resolved. None of these 
warnings (including the ObjectsRequireNonNull) were prevalent when we were writing this code in 
IntelliJ.
5.

