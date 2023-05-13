FITNESS CALCULATOR

About the project
This is a Java GUI based project called Fitness Calculator we used Visual studio for coding and MySQL for the Database. This project allows users to calculate their body mass index (BMI), Body Surface Area (BSA), Corpulence Index (CI), Waist to Hip ratio (WHR), Relative Fat Mass (RFM). 

BMI (Body Mass Index): - It is a measure of body fat based on an individual's weight in relation to their height. It is commonly used to assess whether a person is underweight, normal weight, overweight, or obese. 
BMI is calculated by dividing a person's weight in kilograms by their height in meters squared. The formula is as follows:
BMI = weight (kg) / height (m)^2
BMI values are classified as follows:
- Below 18.5: underweight
- 18.5-24.9: normal weight
- 25-29.9: overweight
- Above 30: obese
It's important to note that BMI is not a perfect measure of body fat and does have its limitations. For example, it doesn't take into account factors such as muscle mass, bone density, or body composition, which can affect a person's overall health. It is always best to consult with a healthcare professional for a more accurate assessment of an individual's health.

Body Surface Area (BSA): - It is a measure of the total surface area of a person's body. It is commonly used in medical settings to calculate medication dosages, assess the severity of burns, and determine the appropriate size of medical equipment. There are several ways to calculate BSA, but one of the most widely used methods is the DuBois formula, which uses a person's height and weight to estimate BSA. The formula is as follows:
BSA (m²) = 0.20247 x height (m)^0.725 x weight (kg)^0.425
For example, a person who is 1.8 meters tall and weighs 75 kilograms would have a BSA of approximately 1.89 m², calculated as follows:
BSA = 0.20247 x 1.8^0.725 x 75^0.425
BSA = 0.20247 x 1.53 x 5.57
BSA = 1.89 m²
It's important to note that while BSA can be a useful measure in medical settings, it has limitations and may not be appropriate for everyone. It does not take into account factors such as age, gender, or body composition, which can affect a person's overall health. It is always best to consult with a healthcare professional for a more accurate assessment of an individual's health.

Corpulence Index (CI): - It is a measure of body fat that takes into account both weight and height. It is similar to BMI but provides a more accurate representation of body fat distribution.  CI is calculated by dividing a person's weight in kilograms by their height in meters cubed. The formula is as follows:
CI = weight (kg) / height (m)^3
CI values are classified as follows:
- Below 11: severe thinness
- 11-16: moderate thinness
- 16-18.5: mild thinness
- 18.5-25: normal weight
- 25-30: overweight
- Above 30: obese
Like BMI, CI has its limitations and does not take into account factors such as muscle mass or body composition. It is always best to consult with a healthcare professional for a more accurate assessment of an individual's health.

Waist to Hip ratio (WHR): - It is a measure of body fat distribution that compares the circumference of a person's waist to their hips. It is commonly used to assess the risk of developing certain health conditions, such as heart disease and diabetes. WHR is calculated by dividing the circumference of a person's waist by the circumference of their hips. The formula is as follows:
WHR = waist circumference (cm) / hip circumference (cm)
For example, if a person has a waist circumference of 80 cm and a hip circumference of 100 cm, their WHR would be 0.8.
WHR values are classified as follows:
- For men, a WHR of 0.9 or higher is considered high and indicates a greater risk of health problems.
- For women, a WHR of 0.85 or higher is considered high and indicates a greater risk of health problems.
It's important to note that WHR is not a perfect measure of body fat distribution and does have its limitations. It does not take into account factors such as age, gender, or body composition, which can affect a person's overall health. It is always best to consult with a healthcare professional for a more accurate assessment of an individual's health.


Relative Fat Mass (RFM): - It is a measure of body fat that was developed as an alternative to Body Mass Index (BMI). It is designed to provide a more accurate measure of body fat percentage and fat mass. RFM is calculated using a formula that takes into account a person's height and waist circumference. The formula is as follows:
RFM = (waist circumference / (1.68 x √height)) - 18
For example, if a person has a waist circumference of 80 cm and a height of 1.8 meters, their RFM would be calculated as follows:
RFM = (80 / (1.68 x √1.8)) - 18
RFM = (80 / (1.68 x 1.34)) - 18
RFM = (80 / 2.252) - 18
RFM = 13.67
RFM values are classified as follows:
- For men, an RFM of 20-25 is considered healthy, while an RFM over 30 indicates obesity.
- For women, an RFM of 25-30 is considered healthy, while an RFM over 35 indicates obesity.
RFM has been shown to be a more accurate measure of body fat percentage than BMI, as it takes into account differences in body composition and fat distribution. However, like all measures of body fat, it has its limitations and should be interpreted in the context of an individual's overall health and other risk factors.

Working Process of Fitness Calculator

Here in this project we have two users- an admin and an end user. 

Working of An Administrator
• There can be only one Administrator, who can sign in using the admin login page. • The administrator has access to every user's and module's data. • He or she is also capable of updating, erasing, and saving data. • The administrator is the one with all the information but doesn't mess with the users' private information. 

Working of an User
• The only "users" in this context are actual users of the application.
• A user can sign up before logging in. 
• After logging in, the user will be taken to the home page, where they can choose alternatives to evaluate their level of fitness, comprising BMI, BSA, RFM, CI, and WaistHipIndex. 
• One can view their body report after selecting the aforementioned preferred options and save it.  


Conclusion
These measurements can be useful for assessing general health and fitness, it is important to remember that they have limitations and do not provide a complete picture of an individual's health. It is always best to consult with a healthcare professional for a more accurate assessment of one's health and fitness levels.  


















