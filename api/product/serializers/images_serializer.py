from rest_framework import serializers
from product.models.images import Images
from product.serializers.poster_serializer import PosterSerializer

class ImagesSerializer(serializers.ModelSerializer):
    posters = PosterSerializer(many=True)

    class Meta:
        model = Images
        fields = ['id', 'posters']
        extra_kwargs = {'slug': {'required': False}}
