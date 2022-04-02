curl -sk "https://localhost:8080/todo/default/?action_by=user1&status=NEW"

curl -sk -H"Content-Type: application/json" -X POST https://localhost:8080/todo/default/ -d '{
     "description": "Buy Groceries",
     "action_by": "user1",
     "created": "2020-03-20T00:00:00Z",
     "status": "NEW",
     "updated": "2020-03-20T00:00:00Z"
}'
