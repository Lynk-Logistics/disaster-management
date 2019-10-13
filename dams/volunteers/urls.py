from django.conf.urls import url
from . import views

urlpatterns = [
    url('volunteer/signup$', views.signup),
    url('volunteer/login$', views.login),
    url('volunteer/[0-9]+', views.update_volunteer)
]
