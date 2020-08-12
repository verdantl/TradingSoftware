#Design Patterns
#Dependency Injection
We used Dependency Injection in many of our Use Case methods which took in a List. We often 
used an ArrayList in place.
#Factory Method
We created a class called DialogFactory that created Dialogs for many of our Activities.
This helped resolve much of our duplicate code and many constructor calls being called in many 
activities that were using the Dialog popup from Android.
#Observer 
Firstly, we used Button onClick Listeners that were built into Android Studio. This allowed us to
launch Activities from an entirely separate Activity.
In addition, we created our own form of the Observer design pattern with regard to the abstract 
BundleActivity class that fit with our Android program design. We update the Use Case Classes
whenever we press the back button in order to update the bundle inside the BundleActivity class.