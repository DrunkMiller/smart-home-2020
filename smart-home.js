{
  "decorators": [
    {
      "type": "ru.sbt.mipt.oop.components.decorators.HomeComponentWithSignalization",
      "id": ""
    }
  ],
  "type": "ru.sbt.mipt.oop.components.SmartHome",
  "data": {
    "rooms": [
      {
        "type": "ru.sbt.mipt.oop.components.Room",
        "data": {
          "lights": [
            {
              "type": "ru.sbt.mipt.oop.components.Light",
              "data": {
                "isOn": false,
                "id": "1"
              }
            },
            {
              "type": "ru.sbt.mipt.oop.components.Light",
              "data": {
                "isOn": true,
                "id": "2"
              }
            }
          ],
          "doors": [
            {
              "type": "ru.sbt.mipt.oop.components.Door",
              "data": {
                "id": "1",
                "isOpen": false
              }
            }
          ],
          "name": "kitchen"
        }
      },
      {
        "type": "ru.sbt.mipt.oop.components.Room",
        "data": {
          "lights": [
            {
              "type": "ru.sbt.mipt.oop.components.Light",
              "data": {
                "isOn": true,
                "id": "3"
              }
            }
          ],
          "doors": [
            {
              "type": "ru.sbt.mipt.oop.components.Door",
              "data": {
                "id": "2",
                "isOpen": false
              }
            }
          ],
          "name": "bathroom"
        }
      },
      {
        "type": "ru.sbt.mipt.oop.components.Room",
        "data": {
          "lights": [
            {
              "type": "ru.sbt.mipt.oop.components.Light",
              "data": {
                "isOn": false,
                "id": "4"
              }
            },
            {
              "type": "ru.sbt.mipt.oop.components.Light",
              "data": {
                "isOn": false,
                "id": "5"
              }
            },
            {
              "type": "ru.sbt.mipt.oop.components.Light",
              "data": {
                "isOn": false,
                "id": "6"
              }
            }
          ],
          "doors": [
            {
              "type": "ru.sbt.mipt.oop.components.Door",
              "data": {
                "id": "3",
                "isOpen": true
              }
            }
          ],
          "name": "bedroom"
        }
      },
      {
        "type": "ru.sbt.mipt.oop.components.Room",
        "data": {
          "lights": [
            {
              "type": "ru.sbt.mipt.oop.components.Light",
              "data": {
                "isOn": false,
                "id": "7"
              }
            },
            {
              "type": "ru.sbt.mipt.oop.components.Light",
              "data": {
                "isOn": false,
                "id": "8"
              }
            },
            {
              "type": "ru.sbt.mipt.oop.components.Light",
              "data": {
                "isOn": false,
                "id": "9"
              }
            }
          ],
          "doors": [
            {
              "type": "ru.sbt.mipt.oop.components.Door",
              "data": {
                "id": "4",
                "isOpen": false
              }
            }
          ],
          "name": "hall"
        }
      }
    ]
  }
}