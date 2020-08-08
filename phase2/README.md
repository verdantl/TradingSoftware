1. The decision to keep Controllers and Presenters together the same class was not an easy one. 
However, we realized that separating them would lead to the opposite of what we intend to do.
Separating the controller and presenter is done with the intention of separating responsibility 
and making sure the code is more extensible. However, since the XML files as well as the Listeners
all depend on the Activity, separating would lead to several major problems:
    (a) Whether or not we made the Activity the presenter or the controller, the new, separated class
    would be far too small and comprised of only one method(which is a code smell)
    (b) Making the Activity the controller and making a new Presenter class would cause coupling 
    (since the xml files depend on the properties of the Activity, we would have to pass in a 
    parameter of "this" into the presenter). This would be very poor SOLID design because if we wanted
    to add more elements, we would have to change both classes and it would completely destroy the
    purpose of the Presenter class.
    (c) If we made the Activity the presenter instead, this 
    (d) Most importantly, it seems that separating them would make the code more extensible and
    follow SOLID. However, we realized that if we separated the classes and ever wanted to change
    how something was presented, changes would have to be made to both classes either way. It was
    a better idea to just make multiple activities instead of  
    (e) We feel that in this particular situation, since we are using the Android platform for our
    program, division of responsibilities is accomplished by creating many different Activities
    each responsible for a small part of the program instead of having larger Controllers and 
    Presenters.
Ultimately, this decision was made after discussion with the professor and by balancing code smells 
and SOLID principles against clean architecture structure. We thought the former two design principles
 were more important than the latter in this case.

2. Some of the abstract classes are small, but we decided to implement them to reduce the amount
of code that was being repeated in many activities as well as provide a template for better
extensibility.

3. We had to put two instances of the String keys for the use cases in BundleActivity and in 
ConfigGateway. We had a BundleActivity class to keep the String keys in to reduce the hard coding
and maintain consistency across all of the Activities. We also created this class to take care of 
the bundle variable and reduce the repeated bundle code.
