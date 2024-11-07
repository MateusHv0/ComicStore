from rest_framework import serializers
from product.models.product import Product, Category
from product.serializers.category_serializer import CategorySerializer
from product.models.details import Details
from product.serializers.details_serializer import DetailsSerializer
from product.serializers.images_serializer import ImagesSerializer


class ProductSerializer(serializers.ModelSerializer):
    categories = CategorySerializer(many=True, read_only=True)
    categories_id = serializers.PrimaryKeyRelatedField(
        queryset=Category.objects.all(), write_only=True, many=True
    )
    details = DetailsSerializer(many=False, read_only=True)
    details_id = serializers.PrimaryKeyRelatedField(
        queryset=Details.objects.all(), write_only=True, many=False
    )
    images = ImagesSerializer(many=True, read_only=True)

    class Meta:
        model = Product
        fields = [
            "id",
            "name",
            "price",
            "stock",
            "overview",
            "active",
            "categories",
            "categories_id",
            "details",
            "details_id",
            "poster",
            "images",
        ]

    def create(self, validated_data):
        category_ids = validated_data.pop("categories_id", [])
        details_ids = validated_data.pop("details_id", [])
        product = Product.objects.create(**validated_data)

        if details_ids:
            product.details.set(details_ids)

        if category_ids:
            product.categories.set(category_ids)

        return product
