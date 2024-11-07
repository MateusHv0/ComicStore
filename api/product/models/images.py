from django.db import models

from product.models import Product


class Images(models.Model):
    id = models.AutoField(primary_key=True)
    product = models.ForeignKey(
        Product, related_name="images", on_delete=models.CASCADE
    )

    def __str__(self):
        return "Objeto"
