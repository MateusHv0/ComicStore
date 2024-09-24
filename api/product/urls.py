from django.urls import path, include
from rest_framework import routers

from product import viewsets

router = routers.SimpleRouter()
router.register('product', viewsets.ProductViewSet, basename='product')
router.register('category', viewsets.CategoryViewSet, basename='category')
router.register('details', viewsets.DetailsViewSet, basename='details')
router.register('images', viewsets.ImagesViewSet, basename='images')


urlpatterns = [
    path('', include(router.urls))
]