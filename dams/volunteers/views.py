from django.shortcuts import render
import json
from .models import Volunteers, Volunteer_Supplier_Victim
from django.http import HttpResponse

# Create your views here.


def signup(request):
    received_json_data = json.loads(request.body)
    print("Issue possible in location")
    volunt_obj = Volunteers()
    volunt_obj.name = received_json_data['name']
    volunt_obj.number = received_json_data['mobile_number']
    volunt_obj.location = received_json_data['location']
    volunt_obj.password = received_json_data['password']
    volunt_obj.save()
    print("Issue can be here in primary key victim", volunt_obj.pk)
    return HttpResponse(json.dumps({"volunteer_id": volunt_obj.id}), content_type="application/json")


def login(request):
    received_json_data = json.loads(request.body)
    number = received_json_data['mobile_number']
    password = received_json_data['password']
    print("1")
    volunt_data_list_active = Volunteers.objects.get(
        number=number, password=password)
    print("2")
    va = Volunteer_Supplier_Victim.objects.get(volunteer_id=volunt_data_list_active.id)
    print("3")
    volunteer_details = {
        "id": volunt_data_list_active.id,
        "name": volunt_data_list_active.name,
        "location": volunt_data_list_active.location,
        "status": va.status

    }
    return HttpResponse(json.dumps({"volunteer_details": volunteer_details}), content_type="application/json")


def update_volunteer(request):
    received_json_data = json.loads(request.body)
    print("1", received_json_data)
    path = request.path
    volunteer_id = path.split('/')[-2]
    vol_obj = Volunteers.objects.get(id=volunteer_id)
    vol_obj.transportation = received_json_data['has_vehicle']
    vol_obj.location = received_json_data['location']
    vol_obj.save()
    return HttpResponse(status=200)
