import requests

def sendMessages(victim_location, victim_mob, supplier_location, supplier_mob, volunteer_location, volunteer_mob):
    url = "https://www.fast2sms.com/dev/bulk"
    numbers = "{}".format(volunteer_mob)
    payload = "sender_id=FSTSMS&message={}&language=english&route=p&numbers={}".format(
        "Hi your supplier location is {} and mobile_no is {} and your victim_location is{} and his mobile no is{}".format(supplier_location, supplier_mob, victim_location, victim_mob), numbers)
    headers = {
        'authorization': "EGoanmHNs9qkv7jrTPRUZ8luVtfiYKI3OAeCJyxM6whBDXQ4WFGnrWH71ChiKUfS34vtOemFNajsMJbL",
        'Content-Type': "application/x-www-form-urlencoded",
        'Cache-Control': "no-cache",
    }

    response = requests.request("POST", url, data=payload, headers=headers)

    print(response.text)

    numbers = "{}".format(supplier_mob)
    payload = "sender_id=FSTSMS&message={}&language=english&route=p&numbers={}".format(
        "Hi the volunteer location is {} and mobile_no is {} and the victim_location is{} and his mobile no is{}".format(volunteer_location, volunteer_mob, victim_location, victim_mob), numbers)
    headers = {
        'authorization': "EGoanmHNs9qkv7jrTPRUZ8luVtfiYKI3OAeCJyxM6whBDXQ4WFGnrWH71ChiKUfS34vtOemFNajsMJbL",
        'Content-Type': "application/x-www-form-urlencoded",
        'Cache-Control': "no-cache",
    }

    response = requests.request("POST", url, data=payload, headers=headers)

    print(response.text)

    numbers = "{}".format(victim_mod)
    payload = "sender_id=FSTSMS&message={}&language=english&route=p&numbers={}".format(
        "Help is on the way!! The Volunteer location is {} and mobile_no is {} and the Supplier Location is{} and his mobile no is{}".format(volunteer_location, volunteer_mob, supplier_location, supplier_mob), numbers)
    headers = {
        'authorization': "EGoanmHNs9qkv7jrTPRUZ8luVtfiYKI3OAeCJyxM6whBDXQ4WFGnrWH71ChiKUfS34vtOemFNajsMJbL",
        'Content-Type': "application/x-www-form-urlencoded",
        'Cache-Control': "no-cache",
    }

    response = requests.request("POST", url, data=payload, headers=headers)

    print(response.text)


