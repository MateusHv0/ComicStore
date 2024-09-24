from django.db import models

class Details(models.Model):
    id = models.AutoField(primary_key = True)
    name = models.CharField(max_length=100)
    banner = models.CharField(max_length=300, blank=True, null=False)
    description = models.TextField(max_length=500, blank=True, null=False)

    def __str__(self):
        return self.name
