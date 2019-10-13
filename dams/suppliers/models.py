from django.db import models
from location_field.models.plain import PlainLocationField
from victims.models import Needs

# Create your models here.


class Suppliers(models.Model):
    id = models.AutoField(primary_key=True)
    name = models.CharField(max_length=255)
    number = models.CharField(max_length=255, default=None, null=True)
    email = models.EmailField(max_length=60)
    password = models.CharField(max_length=64, default=None, null=True)
    area = models.CharField(max_length=255)
    donation = models.ForeignKey(Needs, on_delete=models.CASCADE, db_column='Need_id', default=None, null=True)
    location = PlainLocationField(based_fields=['city'], zoom=7)
    quantity = models.IntegerField(default=0)

    def is_upperclass(self):
        return self.donation_type in (self.MEDICAL_ASSISTANCE, self.TRANSPORTATION, self.FOOD, self.CLOTHES, self.MEDICINES)

    def __str__(self):
        return self.name

    def pub_date_pretty(self):
        return self.pub_date.strftime('%b %e %Y')

    def summary(self):
        return self.body[:100]

    class Meta:
        db_table = 'Suppliers'
