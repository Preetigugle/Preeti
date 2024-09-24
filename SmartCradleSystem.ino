#include <Servo.h>
#include <NewPing.h>

// Pin definitions
const int trigPin = 9;        // Ultrasonic Trig Pin
const int echoPin = 10;       // Ultrasonic Echo Pin
const int soundSensorPin = 7; // Sound Sensor Digital Output Pin
const int buzzerPin = 8;      // Buzzer Pin
const int servoPin = 3;       // Servo Motor Pin

// Ultrasonic sensor parameters
#define MAX_DISTANCE 200      // Maximum distance to detect (in cm)
NewPing sonar(trigPin, echoPin, MAX_DISTANCE);

// Create servo object
Servo servoMotor;

// Thresholds
const int cryingThreshold = 1;  // Sound sensor HIGH signal
const int proximityThreshold = 5; // Distance in cm to trigger the buzzer

void setup() {
  Serial.begin(9600); // Initialize serial communication
  servoMotor.attach(servoPin); // Attach the servo motor
  servoMotor.write(90);        // Set initial position (neutral)
  
  pinMode(soundSensorPin, INPUT);
  pinMode(buzzerPin, OUTPUT);
  Serial.println("System Initialized");
}

void loop() {
  // Measure distance with the ultrasonic sensor
  int distance = sonar.ping_cm();

  // Check if someone is close enough to trigger the buzzer
  if (distance > 0 && distance < proximityThreshold) {
    Serial.println("Human detected"); // Print message to Serial Monitor
    digitalWrite(buzzerPin, HIGH);   // Activate the buzzer
  } else {
    digitalWrite(buzzerPin, LOW);    // Deactivate the buzzer
  }

  // Check if the sound sensor detects a sound
  int soundDetected = digitalRead(soundSensorPin);

  // Debugging output for sound sensor status
  Serial.print("Sound Detected: ");
  Serial.println(soundDetected == HIGH ? "YES" : "NO");

  // If a loud sound is detected
  if (soundDetected == HIGH) {
    Serial.println("Crying detected: Moving Servo");
    
    // Simulate rocking motion of the cradle
    servoMotor.write(0);   // Move servo to 0 degrees
    delay(1000);           // Wait for 1 second
    servoMotor.write(180); // Move servo to 180 degrees
    delay(1000);           // Wait for 1 second
    servoMotor.write(90);  // Return to neutral position
  }

  delay(100); // Small delay to stabilize readings
}
