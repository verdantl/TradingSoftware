
1. Not every activity with a dialog needs a clickable list, but every clickable list needs 
a dialog to display the information after being clicked on.
2. Some of the abstract classes are small, but we decided to implement them to reduce the amount
of code that was being repeated in many activities as well as provide a template for better
extensibility.
3. The decision to keep Controllers and Presenters within the same class was not an easy one. 
However, we realized that separating them would lead to three major problems:
    (a) Having a Presenter that was too small (would be comprised of only one method)
    (b) It would cause coupling (since the xml files depend on the properties of the Activity, we
    would have to pass in a parameter of this into the presenter)
    (c) 