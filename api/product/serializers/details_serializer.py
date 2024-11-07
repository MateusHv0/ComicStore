from rest_framework import serializers

from product.models.details import Details


class DetailsSerializer(serializers.ModelSerializer):
    class Meta:
        model = Details
        fields = [
            "id",
            "name",
            "banner",
            "description",
        ]
        extra_kwargs = {"slug": {"required": False}}
