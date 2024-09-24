# Generated by Django 5.0.6 on 2024-07-07 16:12

from django.db import migrations, models


class Migration(migrations.Migration):

    initial = True

    dependencies = []

    operations = [
        migrations.CreateModel(
            name="Category",
            fields=[
                (
                    "id",
                    models.BigAutoField(
                        auto_created=True,
                        primary_key=True,
                        serialize=False,
                        verbose_name="ID",
                    ),
                ),
                ("name", models.CharField(max_length=100)),
                ("slug", models.SlugField(unique=True)),
                ("description", models.TextField(blank=True, max_length=500)),
                ("active", models.BooleanField(default=True)),
            ],
        ),
        migrations.CreateModel(
            name="Product",
            fields=[
                (
                    "id",
                    models.BigAutoField(
                        auto_created=True,
                        primary_key=True,
                        serialize=False,
                        verbose_name="ID",
                    ),
                ),
                ("name", models.CharField(max_length=100)),
                ("price", models.PositiveIntegerField()),
                ("stock", models.IntegerField()),
                ("description", models.TextField(blank=True, max_length=500)),
                ("active", models.BooleanField(default=True)),
                ("categories", models.ManyToManyField(to="product.category")),
            ],
        ),
    ]