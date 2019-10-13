from django.db import models

# Create your models here.
class ReqTypes(models.Model):
    id = models.AutoField(primary_key=True)
    ReqType = models.CharField(max_length=64)

    def __str__(self):
        return self.name

    def pub_date_pretty(self):
        return self.pub_date.strftime('%b %e %Y')

    def summary(self):
        return self.body[:100]


# Create your models here.
class SubReqTypes(models.Model):
    id = models.AutoField(primary_key=True)
    ReqType = models.ForeignKey(ReqTypes, on_delete=models.CASCADE)
    SubReqTypes = models.CharField(max_length=255)

    def __str__(self):
        return self.name

    def pub_date_pretty(self):
        return self.pub_date.strftime('%b %e %Y')

    def summary(self):
        return self.body[:100]