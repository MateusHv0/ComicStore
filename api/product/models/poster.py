from django.db import models

from product.models import Images


class Posters(models.Model):
    id = models.AutoField(primary_key=True)
    file_path = models.CharField(max_length=300)
    images = models.ForeignKey(Images, related_name="posters", on_delete=models.CASCADE)


def __str__(self):
    return self.file_path
