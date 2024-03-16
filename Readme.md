<div align="center">
    <h2 align="center">Assignment - Tic Tack Toe API and Tests</h2>
    <h3 align="center">Due December 6th</h3>
</div>

<div align="center">

[![License][license-badge]][license] [![Issues][issues-badge]][issues] [![PRs][prs-badge]][prs] [![Evan][evan-badge]][evan]

</div>

## Overview
During the HTML/CSS section of PI1 you created a Tic-Tac-Toe game using javascript. How would that have worked if the FE was connected to some APIs? Let's build it.

## Specifications
1. Have two controllers for this project: GameController and MoveController
2. Autowire a Single Tic-Tac-Toe service into both of the above controllers
3. Use Loggers where needed
4. Write unit tests to ensure functionality and cover all the public methods in the service an all of the endpoints
5. Multiple games can be played simotaniously so you should keep track of them in a list
6. Follow the below API spec:

### Create Game Operation
#### Request Details
- Description: Creates and returns a new game to the caller
- Path: /api/v1/games
- Method: POST
- Body: None

#### Response Details
- Example Response Body JSON:
``` 
{
  "id": 123456789,
  "status": "In-Progress",
  "winner": null,
  "xWins": 0,
  "oWins": 0,
  "ties": 0,
  "board": [
    ["", "", ""],
    ["", "", ""],
    ["", "", ""]
  ]
}
```

### Get Game Operation
#### Request Details
- Description: Returns the game with the provided id
- Path: /api/v1/games/{id}
- Method: GET
- Body: None

#### Response Details
- Throwable Errors:
    - 404 Not Found if game ID doesn't exist
- Example Response Body JSON:
``` 
{
  "id": 123456789,
  "status": "Complete",
  "winner": "Tie",
  "xWins": 2,
  "oWins": 7,
  "ties": 1,
  "board": [
    ["X", "O", "O"],
    ["O", "X", "X"],
    ["X", "X", "O"]
  ]
}
```

### Get All Games Operation
#### Request Details
- Description: Returns all available games
- Path: /api/v1/games
- Method: GET
- Body: None

#### Response Details
- Example Response Body JSON:
``` 
{
  [
    {
      "id": 123456789,
      "status": "Complete",
      "winner": "X",
      "xWins": 8,
      "oWins": 4,
      "ties": 5,
      "board": [
        ["X", "O", "O"],
        ["X", "O", "X"],
        ["X", "X", "O"]
      ]
    }, 
    {
      "id": 456789123,
      "status": "In-Progress",
      "winner": null,
      "xWins": 2,
      "oWins": 7,
      "ties": 1,
      "board": [
        ["X", "O", "O"],
        ["O", "X", "X"],
        ["X", "X", "O"]
      ]
    }
  ]
}
```

### Clear Game Operation
#### Request Details
- Description: Clears the game board and winnner for the given ID and sets status back to In-Progress
- Path: /api/v1/games/clear/{id}
- Method: POST
- Body: None

#### Response Details
- Throwable Errors:
    - 404 Not Found if game ID doesn't exist
- Example Response Body JSON:
``` 
{
  "id": 123456789,
  "status": "In-Progress",
  "winner": null,
  "xWins": 2,
  "oWins": 7,
  "ties": 1,
  "board": [
    ["", "", ""],
    ["", "", ""],
    ["", "", ""]
  ]
}
```

### Reset Game Operation
#### Request Details
- Description: Resets the game board for the given ID, sets status back to In-Progress, clears all stat fields
- Path: /api/v1/games/reset/{id}
- Method: POST
- Body: None

#### Response Details
- Throwable Errors:
    - 404 Not Found if game ID doesn't exist
- Example Response Body JSON:
``` 
{
  "id": 123456789,
  "status": "In-Progress",
  "winner": null,
  "xWins": 0,
  "oWins": 0,
  "ties": 0,
  "board": [
    ["", "", ""],
    ["", "", ""],
    ["", "", ""]
  ]
}
```

### Delete Game Operation
#### Request Details
- Description: Deletes a game with the given ID from memory
- Path: /api/v1/games/{id}
- Method: DELETE
- Body: None

#### Response Details
- Throwable Errors:
    - 404 Not Found if game ID doesn't exist
- Return empty 204 No Content Response

### Make Game Move Operation
#### Request Details
- Description: Allows the user to make a move for a given game
- Other logic: If a winner is the result of this move the given winner stats should be updated, Game should move to Complete status, and a winner should be identified and saved for the game
- Path: /api/v1/moves
- Method: POST
- Body:
``` 
{
  "gameId": 123456789,
  "location": "0,2",
  "player": "X"
}
```
- Body Validation:
    - game ID - required
    - location - required, must match pattern of digit comma digit
    - player - required, must be X or O

#### Response Details
- Throwable Errors:
    - 404 Not Found if game ID doesn't exist
    - 400 Bad Request if game already has a winner
    - 400 Bad Request if body validation is not met
- Example Response Body JSON:
``` 
{
  "id": 123456789,
  "status": "In-Progress",
  "winner": null,
  "xWins": 0,
  "oWins": 0,
  "ties": 0,
  "board": [
    ["", "", "X"],
    ["", "", ""],
    ["", "", ""]
  ]
}
```

## Submission

Your submission should be in GitLab. Please make sure to add the instructor as a collaborator in your GitLab project and send them the Slack link when it is ready for review.

<!--
Link References
-->
[license]: https://gitlab.mccinfo.net/code-school/cohort-4/pi1/java/-/blob/main/LICENSE "Our license"
[issues]: https://gitlab.mccinfo.net/code-school/cohort-4/pi1/java/-/issues "View or log an issue"
[prs]: https://gitlab.mccinfo.net/code-school/cohort-4/pi1/java/-/merge_requests "Feel free to submit a PR!"
[evan]: mailto:estohlmann.mcc@gmail.com "Email with any problems or concerns!"

<!--
Badge References
-->
[license-badge]: https://img.shields.io/badge/License-EULA-A31F34?&style=for-the-badge&labelColor=0057b8
[issues-badge]: https://img.shields.io/badge/issues-report-red.svg?style=for-the-badge&labelColor=0057b8
[prs-badge]: https://img.shields.io/badge/prs-welcomed-green.svg?style=for-the-badge&labelColor=0057b8
[evan-badge]: https://img.shields.io/badge/email-evanstohlmann-6463a9.svg?style=for-the-badge&labelColor=0057b8## 2024_winter_be_class_23
