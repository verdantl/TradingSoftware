#README.
1. The program requires Android Studio (preferably the latest version). It also runs best
 on a Pixel 3a XL, with an API edition of 27. However, other devices are also supported.
2. The project must be run from the Phase 2 folder as the base folder.
3. Currently, we do not allow changes in usernames, but we do offer password changing 
functionality. 
4. We had to put two instances of the String keys for the use cases in BundleActivity and in 
ConfigGateway. The other option would have been to make the String keys public and static. 
5. The ObjectsRequireNonNull method is required for Android in order to remove the warnings. There
are also several warnings that Android Studio picked up in Meeting/MeetingManager
with regards to NullPointerExceptions and unboxing, but they could not be resolved. None of these 
warnings (including the ObjectsRequireNonNull) were prevalent when we were writing this code in 
IntelliJ.
6. There is a functionality to make sure a meeting is only confirmed after the meeting has passed.
For the TA's convenience we have commented out the code responsible for that in EditTradeActivity to
make testing the program easier. If desired, the code can be un-commented so that it checks the date.
7. Please read the design_decisions.md file for an explanation on some of our key decisions,
such as keeping controllers/presenters together, creating BundleActivity and UpdatableBundleActivity
abstract classes, and what actions can reasonably be undone by an Admin.

