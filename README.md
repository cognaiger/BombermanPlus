<!-- PROJECT LOGO -->
<br />
<div align="center">
  <a href="https://github.com/Nhathuy1305/OOP_BOMBERMAN_GAME.git">
    <img src="src/main/resources/logo.png" alt="Logo" width="200" height="200">
  </a>

  <h3 align="center">OOP BOMBERMAN PROJECT</h3>
  <h4 align="center">Team Name: Berserk</h4>
</div>

<!-- TABLE OF CONTENTS -->
# Table of contents 
1. [Introduction](#Introduction)
2. [Game](#Game)
3. [UML-class-diagram](#UML-class-diagram)
4. [Features](#Features)
5. [Challenges](#Challenges)
6. [Acknowledgments](#Acknowledgments)
7. [References](#References)
<!-- <details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#Introduction">Introduction</a>
      <ul>
        <li><a href="#Team-members">Team Members</a></li>
	<li><a href="#installation">Installation</a></li>
	<li><a href="#motivation">Motivation</a></li>
	<li><a href="#task-allocation">Task Allocation</a></li>      
      </ul>
    </li>
    <li><a href="#technologies">Technologies</a></li>
    <li><a href="#uml-class-diagram">UML Class Diagram</a></li>
    <li><a href="#features">Features</a></li>
    <li><a href="#challenges">Challenges</a></li>
    <li><a href="#acknowledgments">Acknowledgments</a></li>
    <li><a href="#references">References</a></li>
  </ol>
</details> -->

<!-- ABOUT THE PROJECT -->

## Introduction <a name="Introduction"></a> :bricks:

<div align="center">
<img src="screenshots/Intro.gif" alt="">
</div>

<div style="text-align:justify">
This is our game project for our Object-Oriented Programming course. Bomberman is an individual game that does not require players have excellent skills or advanced technology. It's a straightforward game that may be enjoyed by people of all ages. As a result of all of these factors, we developed a program that is user-friendly and assists users in having the greatest possible experiences. So, what are we waiting for? Let the story begin!
</div>

### Team Members :couplekiss_man_man:

| Order |      Name       |     MSV     |           Email            |                       Github account                        |
| :---: |:---------------:|:-----------:|:--------------------------:|:-----------------------------------------------------------:|
|   1   | Truong Gia Ngoc |  21020369   |  ngoctruonggia@gmail.com   |          [cognaiger](https://github.com/cognaiger)           |
|   2   | Pham Gia Phong  | ITITIU20165 | hoangbao15082002@gmail.com | [Mr@JEY](https://github.com/PhanNguyenHoangBao-ITITIU20165) |

### Motivation :mechanical_arm:

<div style="text-align:justify">
As a fresher developer, we assume that the Bomberman game is one of the most simple game which helps us in practicing coding with OOP also some significant aspects of the front-end such as: How to render the game? , How frame working in UX/UI? , ….
</div>

<!-- Game -->
<br />

## Game <a name="Game"></a>:joystick:
### Technologies :globe_with_meridians:

- Language: [JAVA](https://www.java.com/en/)
- Framework: [IntelliJ](https://www.jetbrains.com/idea/)
- Library: [FXGL](https://github.com/AlmasB/FXGL)


### How to play ? :video_game:
<div style="text-align:justify">

Players will direct the character's movement in order to place bombs to killing the enemy. After the player has killed all enemies of this level a portal will open for the character to enter in order to advance to the next level (we have 3 levels).
</div>

### Game logic :bulb:

- Character: The character is controlled by button WASD, press SPACE to place bomb. Moreover ,The character will increase strength if he absorbs item.
<div align="center">
<img src="screenshots/Character.gif" alt="">
</div>
<br />

- Bomb : When the bomb explodes, it will create a fire flames, if the character or the enemy hits the fire flames, they will all be destroyed. The length of the flame will increase if the character eats flameitem
<div align="center">
<img src="screenshots/Bomb.gif" alt="">
</div>


<br />

- Enemy: includes 6 types and is divided by level, each type has its own appearance and attribute.
<div align="center">
<img src="screenshots/Ballom.gif" alt="">
<img src="screenshots/Oneal.gif" alt="">
<img src="screenshots/Kondoria.gif" alt="">
<img src="screenshots/Doll.gif" alt="">
</div>


<br />

- Brick : it Can be broken by bombs and can contain items

<br />

- Wall : it cannot be broken to limit the movement of characters and enemies

<br />

## UML Class Diagram<a name="UML-class-diagram"></a>:clipboard:
<!-- ![](UML_Diagrams/Control.jpeg) -->

<div>
	<h3>1. CONTROL</h3>
    <div align="center">
        <img src="UML_Diagrams/Control.png" alt="">
    </div>
    <br />
	<div align="center">------------------------------------</div>
    <br />
	<h3>2. ENTITY</h3>
    <h4>a) Animal</h4>
    <div align="center">
        <img src="UML_Diagrams/Animal.png" alt="">
    </div>
    <h4>b) Intelligent with "Doll" enemy</h4>
    <div align="center">
        <img src="UML_Diagrams/Intelligent_Doll.png" alt="">
    </div>
    <h4>c) Block</h4>
    <div align="center">
        <img src="UML_Diagrams/Block.png" alt="">
    </div>
    <h4>d) Items</h4>
    <div align="center">
        <img src="UML_Diagrams/Items.png" alt="">
    </div>
    <h4>e) Items with Entity</h4>
    <div align="center">
        <img src="UML_Diagrams/Items_Entity.png" alt="">
    </div>
    <h4>f) Animal with Entity</h4>
    <div align="center">
        <img src="UML_Diagrams/Animal_Entity.png" alt="">
    </div>
	<div align="center">------------------------------------</div>
	<h3>3. FEATURES</h3>
    <div align="center">
        <img src="UML_Diagrams/Features.png" alt="">
    </div>
    <br />
	<div align="center">------------------------------------</div>
	<h3>4. GAMERUNNER</h3>
    <div align="center">
        <img src="UML_Diagrams/GameRunner.png" alt="">
    </div>
    <br />
	<div align="center">------------------------------------</div>
	<h3>5. GRAPHICS</h3>
    <div align="center">
        <img src="UML_Diagrams/Graphics.png" alt="">
    </div>
    <br />
	<div align="center">------------------------------------</div>
	<h3>6. LEVELS</h3>
    <div align="center">
        <img src="UML_Diagrams/Levels.png" alt="">
    </div>
    <br />
    <div align="center">------------------------------------</div>
</div>

<br />

<!-- FEATURES -->
## Features<a name="Features"> :triangular_flag_on_post:
- Completed: Basic logic, UX/UI, sound of game
- Incompleted: 2 players, multiplayer through Internet, AI for auto playing…
<br />

<!-- CHALLENGES -->
## Challenges<a name="Challenges">:bangbang:

- Task allocation for each team member
- Communication
- Working environment (Github)
<br />


## References<a name="References">  :eye::tongue::eye:
1. [FXGL tutorials](https://webtechie.be/post/2020-05-07-getting-started-with-fxgl/)
2. [FXGL documents](https://almasb.github.io/FXGL/)

<br />

<p align="right">(<a href="#top">Back to top</a>)</p>

<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->

[contributors-shield]: https://img.shields.io/github/contributors/Nhathuy1305/OOP_BOMBERMAN_GAME.svg?style=for-the-badge
[contributors-url]: https://github.com/Nhathuy1305/OOP_BOMBERMAN_GAME/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/Nhathuy1305/OOP_BOMBERMAN_GAME.svg?style=for-the-badge
[forks-url]: https://github.com/Nhathuy1305/OOP_BOMBERMAN_GAME/network/members
[stars-shield]: https://img.shields.io/github/stars/Nhathuy1305/OOP_BOMBERMAN_GAME.svg?style=for-the-badge
[stars-url]: https://github.com/Nhathuy1305/OOP_BOMBERMAN_GAME/stargazers
[issues-shield]: https://img.shields.io/github/issues/Nhathuy1305/OOP_BOMBERMAN_GAME.svg?style=for-the-badge
[issues-url]: https://github.com//Nhathuy1305/OOP_BOMBERMAN_GAME/issues

