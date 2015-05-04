#include <Servo.h> 

int pinoLed = 13;
int pinoServo = 9;

Servo servo;
 
void setup() 
{
  Serial.begin(9600);
  servo.attach(pinoServo);
  pinMode(pinoLed, OUTPUT);
} 

void loop() 
{
  if (Serial.available() > 0) { 
    char input = (char)Serial.read();
      
    if ((input == 'l') || (input == 'L')) {
      digitalWrite(pinoLed, HIGH);
    }
    
    if ((input == 'd') || (input == 'D')) {
      digitalWrite(pinoLed, LOW);
    }
  }
  
  moverServo();
  
  delay(150);
}

int posicaoServo = 0;
int intervalo = 10;
boolean direcao = true;

void moverServo() {
  if (direcao) {
    posicaoServo = posicaoServo + intervalo;
  } else {
    posicaoServo = posicaoServo - intervalo;
  }
  
  if (posicaoServo <= 0) {
    posicaoServo = 0;
    direcao = true;
  }
  
  if (posicaoServo >= 180) {
    posicaoServo = 180;
    direcao = false;
  }
  
  servo.write(posicaoServo);
  
  Serial.println(posicaoServo);
}

