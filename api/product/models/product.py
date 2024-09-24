from django.db import models
from .category import Category
from .details import Details

class Product(models.Model):
    id = models.AutoField(primary_key = True)
    name = models.CharField(max_length=100)
    price = models.PositiveIntegerField(null=False)
    stock = models.IntegerField(null=True)
    overview = models.TextField(max_length=500, blank=True, null=False)
    poster = models.CharField(max_length=300, blank=True, null=False)
    active = models.BooleanField(default=True)
    categories = models.ManyToManyField(Category, blank=False)
    details = models.OneToOneField(Details, blank=False,  on_delete=models.CASCADE)
