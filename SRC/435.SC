;**
;**	Logics for room 435 -- Cherri's face
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	October 25, 1989
;**


(script#				435)

(include	"face.sh")

(public
	rm435				0
)

;(define	vCherriFace					435)


(synonyms
	(woman cherri )
)

(enum												;**	RoomScript state values
	intro
	goodbye
	outtaHere
)

(local
	readyForDeed
	seenMsg
)


(instance rm435 of Room
	(properties
		picture		435
	)

	(method (init)
		(super init)

		(self
			setRegions		regBigFace,
			setLocales		locGirls,
			setScript 		RoomScript
		)

		(Notify regBigFace EyeWest		149 45)
		(Notify regBigFace EyeEast		180 45)
		(Notify regBigFace EyelidWest	149 42)
		(Notify regBigFace EyelidEast	178 42)
		(Notify regBigFace Nose			163 61)
		(Notify regBigFace Mouth		164 78)
	)
)


(instance RoomScript of Script
	(method (handleEvent event &tmp theObj)
		(if (or	(!= (event type) saidEvent) (event claimed)) (return))

		(cond
			((Said 'fuck')
				(Print "You think to yourself, 
					\"I bet I'd love the taste of a piece of this \"Cherry Tart!\"")
				(PrintP "\"Why, Larry! 
					Shame on you! 
					I can tell just what you're thinking!\"")
				(React rFrown 4)
			)

			((or	(Said '/land, deed, dowry')
					(Said '//land, deed, dowry'))
				(HandsOff)
				(PrintL "\"You know, I think I have some land coming to
					me from a recent, (cough, cough) uh, legal development.
					I really have no use for it.
					Would you like a few acres?\"")
				(PrintP "\"Do I? 
					Why, Larry, I'd do ANYTHING for a little piece of land.
					But I must run; I still must get ready for the next show.\"")
				(= readyForDeed TRUE)
				(React rSmile 33)
				(= state intro)
				(= cycles 22)
			)

			((or	(Said '(out<go), dance, show, date/')
					(Said 'take/girl/home')
					(Said 'ask/girl/date')
					(Said 'go/room, place, comic, club, date, walk, bar, show, dance'))
				(PrintL "\"What if we split this scene and go back to my
					place, Cherri?\"")
				(PrintP "\"From what I've heard, Larry, 
					you no longer have a place!\"")
			)

			((Said 'talk/girl, cherri')
				(if (not seenMsg)
					(= seenMsg TRUE)
					(PrintL "\"So, Cherri; tell me what you want to do with your
						life.\"")
					(PrintP "\"I'd love to quit show business,
						get a little farm some place far out in the country
						where I could be alone,
						and raise organically-grown vegetables, fruits and herbs.\"")
				else
					(PrintL "\"Well, Cherri, I'm just the opposite.
						I have hundreds of acres of land coming into my possession
						soon but I'd love to be in show business.\"")
					(PrintP "\"Oh, Larry! You're so lucky!
						How I wish I had some land.\"")
				)	
				(React rSmile 20)
			)

			((Said 'show/prick')
				(PrintL "\"Hey, Cherri! Take a look at this, baby!\"")
				(PrintP "\"Gee, that's too bad, Larry.\"")
				(React rSmile 20)
			)

			((or	(Said 'love/you')
					(Said '/love/you'))
				(PrintL "\"Baby, I thinks I loves ya!\"")
				(PrintP "\"I'm sure you would!\"")
				(React rLove 44)
			)

			((or	(Said 'stop/look')
					(Said 'look<stop')
					(Said 'look/room')
					(Said 'quit, bye, leave, exit, quit, walk, go'))
				(self changeState goodbye)
			)

			((Said '/house, land, forest, farm')
				(PrintP "\"Oh, yes, Larry.
					That's my goal, you know:
					to quit show biz and move to the country to pursue my dream: 
					slash and burn, pillage and rape agriculture!\"")
			)

			((Said '/dance, show')
				(PrintL "\"I certainly enjoyed your dancing, Cherri!
					I'm somewhat of a dancer myself, you know.\"")
				(PrintP "\"I'll bet you are,
					but you seem to have lost your dancer's physique!\"")
			)

			((Said 'look>')
				(cond
					((Said '/leg')
						(PrintP "\"Oh, Larry!\" smiles Cherri.
							\"Stop looking at me like that!\"")
						(React rSexy 22)
					)

					((Said '/ear')
						(Print "They look delicious.")
					)

					((Said '/tit')
						(Print "\"Perhaps we'll see more of you two later,\"
							you think to yourself.")
						(PrintP "\"Oh, Larry!\" smiles Cherri.
							\"Stop looking at me there!\"")
						(React rOh 12)
					)

					((Said '[/room, casino, hotel, cherri]')
						(Print "You are talking with Cherri Tart,
							the headline dancer in the casino showroom.")
						(PrintP "\"Hello,\" she says with a smile.")
						(React rSmile 33)
					)
				)
			)

			((Said 'give>')
				(= theObj (inventory saidMe))
				(event claimed FALSE)
				(cond
					((Said '[/noword]')
						(Print "What did you want to give her?")
					)
					((not theObj)
						(Print "It's not yours to give.")
					)
					((not	(theObj ownedBy ego))
						(DontHave)
					)
					((== theObj (inventory at iTowel))
						(PrintP "\"Gosh, Larry.
							This towel would be perfect for my new home in the 
							country... if I can ever get out of show business.
							Thanks!\"")
						(PutInRoom iTowel)
						(theGame changeScore -10)
						(React rSmile)
					)
					((== theObj (inventory at iSoap))
						(PrintP "\"If I ever get my own place in the country,
							I'll need soap like this to clean up.\"")
						(PutInRoom iSoap)
						(theGame changeScore -10)
						(React rSmile)
					)
					((== theObj (inventory at iKnife))
						(PrintP "\"I've dreamed of moving to a place,
							way out in the jungle; just a little place with
							anacondas and a picket fence.
							A good sharp ginsu knife will come in handy!\"")
						(PutInRoom iKnife)
						(theGame changeScore -10)
						(React rSmile)
					)
					((== theObj (inventory at iOrchids))
						(PrintP "\"Oh, I just love orchids,
							but unfortunately, I'm allergic!\"")
						(React rFrown)
					)
					(else
						(PrintP "\"Why, thank you, honey,\" says Cherri,
							\"but what I really need is just a little place
							I can call my own!\"")
					)
				)
				(event claimed TRUE)
			)
		)
	)


	(method (changeState newState)
		(switch (= state newState)

			(intro
			)

			(goodbye
				(if readyForDeed
					(if (!= showroomState SRknowsAboutDeed)
						(= showroomState SRknowsAboutDeed)
						(= lawyerState LSfree)			;**	Suzi is now in.
						(= newspaperState NSpComing)	;**	Patti will come soon.
						(theGame changeScore 25)
					)
				else
					(PrintL "\"I hope you find what you're seeking, Cherri\"")
					(PrintP "\"Thanks, Larry!\"")
				)

				(React rSmile 55)
				(= cycles 33)
			)

			(outtaHere
				(curRoom newRoom 420)
			)
		)
	)
)

