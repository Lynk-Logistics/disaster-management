from django.conf.urls import url
from . import views

urlpatterns = [
    url('victim/signup$', views.signup),
    url('victim/login', views.login),
    url('victim/safe_places', views.get_safe_place),
    url('victim/request/[0-9]+$', views.requirement_status),
    url('safe_place', views.safe_place),
    url('victim/[0-9]+/request', views.all_requests),
    url('request/[0-9]+$', views.location_details),
    url('victim/[0-9]+$/request', views.all_requests),
    url('request/[0-9]+$', views.location_details),
    url('victim/[0-9]+$/relocation', views.relocation)
]
