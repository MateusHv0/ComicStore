from rest_framework.response import Response
from rest_framework.viewsets import ModelViewSet
from product.models.images import Images
from product.serializers.images_serializer import ImagesSerializer
from rest_framework.authentication import SessionAuthentication, TokenAuthentication
from rest_framework.permissions import IsAuthenticated
from django_filters.rest_framework import DjangoFilterBackend

class ImagesViewSet(ModelViewSet):
    authentication_classes = [SessionAuthentication, TokenAuthentication]
    permission_classes = [IsAuthenticated]
    filter_backends = [DjangoFilterBackend]
    filterset_fields = ['product']
    pagination_class = None
    serializer_class = ImagesSerializer

    def get_queryset(self):
        return Images.objects.all()

    def list(self, request, *args, **kwargs):
        queryset = self.filter_queryset(self.get_queryset())
        serializer = self.get_serializer(queryset, many=True)
        data = serializer.data[0]

        return Response(data)