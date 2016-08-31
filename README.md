# basicquestionnaire
A starter questionnaire/quiz app on android

##Input questions
Currently reads from a `json` file in the *assets* folder.

##Type of questions
1. Fill in the blank - Simple text with blanks interspersed
2. Multiple choice with multiple selection - Using checkboxes
3. Multiple choice with single selection - Using radiobuttons

##Type of response
Currently string only.


##Input question format
*questions.json*
```json
{
  "questions": [
    {
      "id": 1,
      "type": "blank",
      "question" : "This is the text of the question and _____ is a blank",
      "blanks" : 1
    },
    {
      "id": 2,
      "type": "checkbox",
      "question": "Here is a question of containing checkbox options",
      "options": [
        {"opid":1,"optype":"text","opvalue":"Foo1"},
        {"opid":2,"optype":"text","opvalue":"Foo2"},
        {"opid":3,"optype":"text","opvalue":"Foo3"},
        {"opid":4,"optype":"text","opvalue":"Foo4"}
      ]
    },
    {
      "id": 3,
      "type": "radiobutton",
      "question": "Here is a question of containing radiobutton options",
      "options": [
        {"opid":1,"optype":"text","opvalue":"Bar1"},
        {"opid":2,"optype":"text","opvalue":"Bar2"},
        {"opid":3,"optype":"text","opvalue":"Bar3"},
        {"opid":4,"optype":"text","opvalue":"Bar4"}
      ]
    }
  ]
}
```


##Response format
The responses are saved in a JSON mimicking the question format.
So for the above questions, a response object could look like (notice the **`response`**) :

```json
{
  "questions": [
    {
      "id": 1,
      "type": "blank",
      "question" : "This is the text of the question and _____ is a blank",
      "blanks" : 1,
      "response" : "Filled response"
    },
    {
      "id": 2,
      "type": "checkbox",
      "question": "Here is a question of containing checkbox options",
      "options": [
        {"opid":1,"optype":"text","opvalue":"Foo1"},
        {"opid":2,"optype":"text","opvalue":"Foo2"},
        {"opid":3,"optype":"text","opvalue":"Foo3"},
        {"opid":4,"optype":"text","opvalue":"Foo4"}
      ],
      "response" : "Foo1,Foo2,none,none"
    },
    {
      "id": 3,
      "type": "radiobutton",
      "question": "Here is a question of containing radiobutton options",
      "options": [
        {"opid":1,"optype":"text","opvalue":"Bar1"},
        {"opid":2,"optype":"text","opvalue":"Bar2"},
        {"opid":3,"optype":"text","opvalue":"Bar3"},
        {"opid":4,"optype":"text","opvalue":"Bar4"}
      ],
      "response" : "Bar1"
    }
  ]
}
```
