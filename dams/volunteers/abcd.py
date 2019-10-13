import requests

url = "https://www.fast2sms.com/dev/bulk"

payload = "sender_id=FSTSMS&message=Hiiiiiii&language=english&route=p&numbers=8789813291,9962256516"
headers = {
    'authorization': "EGoanmHNs9qkv7jrTPRUZ8luVtfiYKI3OAeCJyxM6whBDXQ4WFGnrWH71ChiKUfS34vtOemFNajsMJbL",
    'Content-Type': "application/x-www-form-urlencoded",
    'Cache-Control': "no-cache",
    }

response = requests.request("POST", url, data=payload, headers=headers)

print(response.text)
