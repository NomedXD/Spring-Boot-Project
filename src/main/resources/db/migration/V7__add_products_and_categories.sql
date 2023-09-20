DROP TABLE IF EXISTS categories;
CREATE TABLE categories
(
    id       INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name     VARCHAR(45) DEFAULT (NULL),
    sometext VARCHAR(45) DEFAULT (NULL)
);
INSERT INTO categories(name, sometext)
VALUES ('Nike', 'High rated Nike sneakers'),
       ('Vans', 'Vans on the road'),
       ('Adidas', 'Best Adidas'),
       ('ASICS', 'Best seller ASICS'),
       ('Reebok', 'Limited edition Reebok'),
       ('Skechers', 'Check new Skechers collaboration');

INSERT INTO products(name, description, category_id, price)
VALUES ('Nike Cortez',
        'The main characteristic feature of Cortez is the original sole made of two layers of foamy material, differing in density. This technology softened movements while running, increased the wear resistance of the product. For many years, the design of Cortez has hardly changed, only the material has been improved over time. For example, cape-shaped suede or lightweight nylon replaced the original leather shoes. The characteristic deep scars in the form of a "herringbone" on the pillow have been preserved to our time, such an elastic technique does not restrict movement and guarantees perfect adhesion to the surface. Despite the fact that the Nike Cortez line was produced as a running model, it is often used in everyday urban fashion.',
        1, 120),
       ('ASICS Gel-Skycourt',
        'The new model of the Asics brand, thanks to its minimalistic design in white, is suitable for almost any style of casual clothing. It will go equally well with a tracksuit and jeans. The perforation of the leather upper makes the sneakers suitable for the summer period. In addition to the classic appearance, the model is characterized by convenience – gel inserts effectively extinguish shock loads when walking, making the step soft and easy. The durable rubber sole gives a confident grip on urban road surfaces. According to user reviews, the feet in the new ASICS sneakers are really comfortable. They are soft, fit well to the leg, provide it with the necessary support.',
        4, 72.75),
       ('ASICS Lyte Classic',
        'Classic lightweight sneakers, perfect for everyday wear. The model is available in female and male versions, suitable for mild summer, warm spring and autumn. Natural suede and mesh textile material were used to make the top. The sneakers sit comfortably on the foot thanks to the inner backdrop, and the foam midsole provides cushioning and softness of the step. Judging by user reviews, the shoes match the size, suitable for a wide leg. The model is quite popular due to its affordable cost, convenience and good quality.',
        4, 51.96),
       ('ASICS MetaRide',
        'Comfortable running shoes with a streamlined shape, making it easier to travel long distances. A feature of the model is the improved dynamics achieved thanks to the innovative bending of the sole with a displaced center of gravity. It gives an extra boost while running, which especially helps with long-distance races. The model is also suitable for fitness classes, on a treadmill in the gym. The sneakers are designed for people with neutral pronation and hypopronation. Users believe that the shoes are especially well suited for running at a calm pace. They note a pleasant rolling of the foot, excellent cushioning, convenience.',
        4, 155.95),
       ('ASICS Novablast Platinum',
        'Lightweight Novablast Platinum sneakers are an excellent solution for daily jogging for people with neutral pronation. Elastic and soft cushioning without weighting has become possible thanks to the new FlyteFoam Blast foam sole, and a rubber outsole provides a confident grip on asphalt and other hard surfaces. Among the rest of the brand''s sneakers, the model stands out with a somewhat unusual design. The special geometry plays not only a decorative role, but also unloads the muscles of the lower leg, providing a smooth roll from heel to toe. Additional comfort is provided by the mesh upper material.',
        4, 124.75),
       ('Adidas Iniki Runner', 'Iniki Runner are well—deserved old men in terms of design, they were copied straight from the popular running shoes of the 70s. But the retro model is only on the outside, the materials used in the production were the most modern. The sole is made of branded Boost material with excellent cushioning, natural suede on the toe, the back and the grommets, nylon mesh so that the shoes are well ventilated.
Iniki Runner sit comfortably on the leg and fit any style of clothing, besides, they are relatively inexpensive for branded items. If we ignore the taste in design (not everyone will like retro), then the model has only one drawback — these sneakers are a little difficult to clean.',
        3, 120),
       ('Adidas Yung-1',
        'The trend for massive shoes has not gone away and is unlikely to leave us in the near future. If you need the best sneakers from Adidas of this format, then it makes sense to look at Yung-1. This model was released in 2018, but even now it looks fresh, and it does not stand like an airplane wing. "Yangs" have a multi-layered design with three-dimensional inserts of mesh, suede and nubuck. It uses proprietary Adidas Torsion System technology to support and stabilize the foot in a natural position. Sneakers are stylish, and due to the abundance of available colors can fit into almost any modern wardrobe.',
        3, 97),
       ('Adidas NMD R1', 'Adidas has a line of NMD, which includes high-tech casual sneakers. The beginning of this line was laid by the NMD R1 model. They look simple, except that the unusual shape and bulky blocks on the sole are striking. But everyone who has worn it knows: R1 is not so much about design as about convenience.
The sneakers have a completely seamless upper, which is called Primeknit and essentially resembles a sock. The material is elastic, well-blown and comfortably fits the foot. And with a sole made using Boost technology, you can walk all day in these sneakers without the slightest fatigue and harm to your feet. A large number of colors, men''s, women''s and even children''s versions are universal shoes for every day.',
        3, 150.50),
       ('Reebok x Kung Fu Panda Club C85',
        'As the kung fu style unfolds in a comedic and dramatic line thanks to the plot, humor and exotic locations, Panda Po and his chosen family continue the epic adventures that were started more than a decade ago. Reebok released a whole collection of sneakers in 2021 dedicated to the animated film Kung Fu panda. The flagship of this collection is the Reebok Club C 85, made of genuine leather with drawings of Chinese characters in a delicate cream color. The canvas of the model depicts traditional Chinese writing, where each sign also symbolizes a particular Master. Note that the silhouettes are presented in female and male sizes.',
        5, 100.50);

INSERT
INTO images(PATH, CATEGORY_ID, PRODUCT_ID, ISPRIME)
VALUES ('images/sneakers.png', 3, NULL, 1),
       ('images/running shoes.png', 4, NULL, 1),
       ('images/sneakers.png', 5, NULL, 1),
       ('images/running shoes.png', 6, NULL, 1),
       ('images/Nike cortez_prime.jpg', NULL, 8, 1),
       ('images/Nike cortez_2.jpg', NULL, 8, 0),
       ('images/ASICS Gel-Skycourt_prime.jpg', NULL, 9, 1),
       ('images/ASICS Gel-Skycourt_2.jpg', NULL, 9, 0),
       ('images/ASICS Lyte Classic_prime.jpg', NULL, 10, 1),
       ('images/ASICS Lyte Classic_2.jpg', NULL, 10, 0),
       ('images/ASICS MetaRide_prime.jpg', NULL, 11, 1),
       ('images/ASICS MetaRide_2.jpg', NULL, 11, 0),
       ('images/ASICS Novablast Platinum_prime.jpg', NULL, 12, 1),
       ('images/ASICS Novablast Platinum_2.jpg', NULL, 12, 0),
       ('images/Adidas Iniki Runner_prime.jpg', NULL, 13, 1),
       ('images/Adidas Iniki Runner_2.jpg', NULL, 13, 0),
       ('images/Adidas Yung-1_prime.jpg', NULL, 14, 1),
       ('images/Adidas Yung-1_2.jpg', NULL, 14, 0),
       ('images/Adidas NMD R1_prime.jpg', NULL, 15, 1),
       ('images/Adidas NMD R1_2.jpg', NULL, 15, 0),
       ('images/Reebok x Kung Fu Panda Club C85_prime.jpg', NULL, 16, 1);