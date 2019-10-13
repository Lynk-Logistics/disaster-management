from django.db import models
from victims.models import Needs
from victims.models import Victims
from suppliers.models import Suppliers
from victims.models import SafeLocations
from location_field.models.plain import PlainLocationField

# Create your models here.


class Volunteers(models.Model):
    id = models.AutoField(primary_key=True)
    name = models.CharField(max_length=255)
    number = models.CharField(default=None, null=True, max_length=255)
    email = models.EmailField(max_length=60)
    area = models.CharField(max_length=255)
    location = PlainLocationField(based_fields=['area'], zoom=7)
    type = models.CharField(max_length=255, default=None, null=True)
    password = models.CharField(max_length=64, default=None, null=True)
    transportation = models.BooleanField(default=False)

    def __str__(self):
        return self.name

    def pub_date_pretty(self):
        return self.pub_date.strftime('%b %e %Y')

    def summary(self):
        return self.body[:100]

    class Meta:
        db_table = 'Volunteers'


class Volunteer_Supplier_Victim(models.Model):
    id = models.AutoField(primary_key=True)
    requirement_id = models.ForeignKey(Needs, on_delete=models.CASCADE, db_column='Need_id')
    volunteer_id = models.ForeignKey(Volunteers, on_delete=models.CASCADE, db_column='Volunteer_id')
    supplier_id = models.ForeignKey(Suppliers, on_delete=models.CASCADE, db_column='Supplier_id')
    victims_id = models.ForeignKey(Victims, on_delete=models.CASCADE, db_column='Victim_id')
    status = models.IntegerField(default=0)


    class Meta:
        db_table = 'Volunteer_Supplier_Victim'
