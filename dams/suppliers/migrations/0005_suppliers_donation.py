# Generated by Django 2.2.5 on 2019-10-12 20:55

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    dependencies = [
        ('victims', '0005_auto_20191012_1927'),
        ('suppliers', '0004_auto_20191012_1844'),
    ]

    operations = [
        migrations.AddField(
            model_name='suppliers',
            name='donation',
            field=models.ForeignKey(db_column='Need_id', default=None, null=True, on_delete=django.db.models.deletion.CASCADE, to='victims.Needs'),
        ),
    ]
