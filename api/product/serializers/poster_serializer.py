from rest_framework import serializers

from product.models.poster import Posters


class PosterSerializer(serializers.ModelSerializer):
    class Meta:
        model = Posters
        fields = [
            'id',
            'file_path',
        ]
        extra_kwargs = {'slug': {'required': False}}

