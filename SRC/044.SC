;**
;**	Logics for script 44 -- Weaving script
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	October 1, 1989
;**


(script#			WEAVING)

(public
	WeavingScript		0
)


(define	vLweavingSkirt				717)
	(define	lProduceIt			0)
	(define	lWeaving				1)
	(define	lSkirtDone			2)
	(define	lSkirtIntoCoat		3)


(local
	oldIllegalBits
)


(instance WeavingScript of Script
	(method (changeState newState)
		(switch (= state newState)

			(0
				(HandsOff)
				(= oldGameSpeed (theGame setSpeed 6))
				(= oldIllegalBits (ego illegalBits))
				(ego
					view			vLweavingSkirt,
					loop			lProduceIt,
					cel			0,
					cycleSpeed	1,
					setCycle		EndLoop self,
				)
				(Print "You carefully weave the wild grass
					into what you hope will pass for
					a primitive, island native outfit."
					#icon iGrass 0 0
				)
			)

			(1
				(ego
					loop			lWeaving,
					cel			0,
					setCycle		Forward,
				)
				(delay 3)
			)

			(2
				(ego
					loop			lSkirtDone,
				)
				(delay 2)
			)

			(3
				(ego
					loop			lSkirtIntoCoat,
					cel			0,
					setCycle		EndLoop self,
				)
			)

			(4
				((Inventory at iGrass)
					view		ivGrassSkirt ,
;**						said		'/skirt, grass'
				)
				(Format ((Inventory at iGrass) name) "Grass Skirt")
				(theGame changeScore 30)
				(NormalEgo facingRight)
				(ego
					illegalBits		oldIllegalBits,
				)
				(theGame 
					setScript 			FALSE,
					setSpeed 			oldGameSpeed,
				)
				(DisposeScript WEAVING)
			)
		)
	)
)

