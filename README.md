# Star Wars Resistance Social Network 

![Resistance_Emblem_Star_Wars svg](https://github.com/neonabuko/new-rebels-network/assets/83613676/b79875f4-1506-4eaa-9e89-bfaaa842f569)

_A REST API challenge._

> The empire continues its relentless struggle to conquer the galaxy. As a resistance soldier, I have been assigned to develop a system for sharing resources among rebels.

### This project currently implements:
- [x] Java
- [x] Maven 
- [x] Spring Boot
- [x] Spring Data
- [x] Hibernate (H2)

## Use Cases
- Register rebels
- Update a rebel's location
- Report a Rebel as traitor
- Trade
- Show:
  1. All allies;
  2. Percentages of traitors/allies;
  3. Average of items per rebel.

## Main Rules
1. Traitors can't trade
2. Traitors can't show up on reports
3. Rebels trade up to 4 item types: food, water, ammo, and weapon
4. Both parties must offer the same amount of points
   
  | Item  | Points |
  | ----- | :------: |
  | Food    | 1 |
  | Water   | 2 |
  | Ammo    | 3 |
  | Weapon  | 4 |
