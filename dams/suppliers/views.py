from django.shortcuts import render
import json
from .models import Suppliers
from victims.models import Needs
from volunteers.models import Volunteer_Supplier_Victim
from volunteers.models import *
from django.http import HttpResponse

# Create your views here.


def signup(request):
    received_json_data = json.loads(request.body)
    name = received_json_data['name']
    number = received_json_data['mobile_number']
    location = received_json_data['location']
    password = received_json_data['password']
    print("Issue possible in location")
    supp_obj = Suppliers()
    supp_obj.name = name
    supp_obj.number = number
    supp_obj.location = location
    supp_obj.save()
    print("Issue can be here in primary key victim", supp_obj.id)
    return HttpResponse(json.dumps({"suppliers_id": supp_obj.id}), content_type="application/json")


def login(request):
    received_json_data = json.loads(request.body)
    number = received_json_data['mobile_number']
    password = received_json_data['password']
    try:
        print("0")
        supplier_data_list_active = Suppliers.objects.get(
            number=number, password=password)
        print("1")
        va = Volunteer_Supplier_Victim.objects.get(
            supplier_id=supplier_data_list_active.id)
        print("2")
        result_active = supplier_data_list_active
        supplier_details = {
            "id": supplier_data_list_active.id,
            "name": supplier_data_list_active.name,
            "quantity": supplier_data_list_active.quantity,
            "location": supplier_data_list_active.location,
            "requirement_id": va.requirement_id.type
        }
        return HttpResponse(json.dumps({"supplier_details": supplier_details}), content_type="application/json")
    except Exception as e:
        return HttpResponse(status=401)


def insert_goods(request):
    print("1")
    received_json_data = json.loads(request.body)
    path = request.path
    update_id = path.split('/')[-2]
    goods = received_json_data['goods_type']
    res = Needs.objects.get(sub_type=goods)
    quantity = received_json_data['quantity']
    try:
        sup_upd = Suppliers.objects.get(id=update_id)
    except Exception as e:
        print(e)
        return HttpResponse(status=400)
    sup_upd.quantity = quantity
    sup_upd.donation = res
    sup_upd.save()
    return HttpResponse(status=200)
